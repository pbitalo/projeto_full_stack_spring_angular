import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { VendasService } from '../servicos/vendas.service';
import { FormBuilder, Validators } from '@angular/forms';
import { VendasModelo } from '../modelo/vendas.model';
import { ClienteModelo } from '../../clientes/modelo/cliente.model';
import { ClientesService } from '../../clientes/servicos/clientes.service';
import { ProdutosService } from '../../produtos/servicos/produtos.service';
import { ProdutoModelo } from '../../produtos/modelo/produto.model';

@Component({
  selector: 'app-vendas-form',
  templateUrl: './vendas-form.component.html',
  styleUrls: ['./vendas-form.component.css'],
})
export class VendasFormComponent extends CrudComponent {
  items: VendasModelo[];
  listaClientes: ClienteModelo[];
  listaProdutos: ProdutoModelo[];

  constructor(
    protected servicos: VendasService,
    protected injector: Injector,
    private formBuilder: FormBuilder,
    private servicoCliente: ClientesService,
    private servicoProduto: ProdutosService
  ) {
    super(servicos, injector);
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [],
      bandeira: ['', Validators.required],
      dataColeta: ['', Validators.required],
      valorCompra: ['', Validators.required],
      valorVenda: ['', Validators.required],
      clienteId: ['', Validators.required],
      produtoId: ['', Validators.required],
    });
  }

  editarItem(item: any) {
    item.produtoId = item.produto.id;
    item.clienteId = item.cliente.id;
    this.formulario.patchValue(item);
  }

  mudarPagina(event) {
    this.config.currentPage = event;
    this.pegarVendas();
  }

  pegarDados() {
    this.pegarVendas();
    this.servicoCliente.pegarDados({ pegarTodos: true }).subscribe((result) => {
      this.listaClientes = result.data.content as Array<ClienteModelo>;
    });
    this.servicoProduto.pegarDados().subscribe((result) => {
      this.listaProdutos = result as Array<ProdutoModelo>;
    });
  }

  pegarVendas() {
    this.servicos
      .pegarDados({ pag: this.config.currentPage, pegarTodos: false })
      .subscribe((result) => {
        this.items = result.data.content as Array<VendasModelo>;
        this.config.totalItems = result.data.totalElements;
      });
  }
}
