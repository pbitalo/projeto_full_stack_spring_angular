import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { ClientesService } from '../servicos/clientes.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ClienteModelo } from '../modelo/cliente.model';

@Component({
  selector: 'app-clientes-form',
  templateUrl: './clientes-form.component.html',
  styleUrls: ['./clientes-form.component.css'],
})
export class ClientesFormComponent extends CrudComponent {
  items: ClienteModelo[];
  constructor(
    protected servicos: ClientesService,
    protected injector: Injector,
    private formBuilder: FormBuilder
  ) {
    super(servicos, injector);
  }

  mudarPagina(event) {
    this.config.currentPage = event;
    this.pegarDados();
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [],
      nome: ['', Validators.required],
      instalacaoCodigo: ['', Validators.required],
      siglaRegiao: ['', Validators.required],
      siglaEstado: ['', Validators.required],
      municipio: ['', Validators.required],
    });
  }

  pegarDados() {
    this.servicos
      .pegarDados({ pag: this.config.currentPage, pegarTodos: false })
      .subscribe((result) => {
        this.items = result.data.content as Array<ClienteModelo>;
        this.config.totalItems = result.data.totalElements;
      });
  }
}
