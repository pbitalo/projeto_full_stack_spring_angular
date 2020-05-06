import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { FormBuilder } from '@angular/forms';
import { HistoricoService } from '../servicos/historico.service';
import { HistoricoModelo } from '../modelo/historico.model';
import { ProdutosService } from '../../produtos/servicos/produtos.service';
import { ProdutoModelo } from '../../produtos/modelo/produto.model';

@Component({
  selector: 'app-historico-form',
  templateUrl: './historico-form.component.html',
  styleUrls: ['./historico-form.component.css'],
})
export class HistoricoFormComponent extends CrudComponent {
  items: HistoricoModelo[];
  listaProdutos: ProdutoModelo[];
  estaFiltrando = false;

  constructor(
    protected servicos: HistoricoService,
    protected injector: Injector,
    private formBuilder: FormBuilder,
    private servicoProduto: ProdutosService
  ) {
    super(servicos, injector);
  }

  mudarPagina(event) {
    this.config.currentPage = event;
    if (!this.estaFiltrando) {
      this.pegarHistorico();
    } else {
      this.filtrarDados();
    }
  }

  pegarDados() {
    this.pegarHistorico();
    this.servicoProduto.pegarDados().subscribe((result) => {
      this.listaProdutos = result.data as Array<ProdutoModelo>;
    });
  }

  pegarHistorico() {
    this.servicos
      .pegarDados({ pag: this.config.currentPage })
      .subscribe((result) => {
        this.items = result.data.content as Array<HistoricoModelo>;
        this.config.totalItems = +result.data.totalElements;
      });
  }

  filtrarDados(viaBotao = false) {
    if (viaBotao) {
      this.config.currentPage = 1;
      this.estaFiltrando = true;
    }
    this.servico
      .pegarDadosAlternativosById(
        this.formulario.getRawValue().produtoId,
        'listaHistoricoPreco/',
        { pag: this.config.currentPage }
      )
      .subscribe((result) => {
        this.items = result.data.content as Array<HistoricoModelo>;
        this.config.totalItems = +result.data.totalElements;
      });
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      produtoId: [''],
    });
  }
}
