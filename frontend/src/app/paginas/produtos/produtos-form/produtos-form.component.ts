import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { Validators, FormBuilder } from '@angular/forms';
import { ProdutosService } from '../servicos/produtos.service';
import { ProdutoModelo } from '../modelo/produto.model';

@Component({
  selector: 'app-produtos-form',
  templateUrl: './produtos-form.component.html',
  styleUrls: ['./produtos-form.component.css'],
})
export class ProdutosFormComponent extends CrudComponent {
  items: ProdutoModelo[];

  constructor(
    protected servicos: ProdutosService,
    protected injector: Injector,
    private formBuilder: FormBuilder
  ) {
    super(servicos, injector);
  }

  pegarDados() {
    this.servicos.pegarDados().subscribe((result) => {
      this.items = result.data as Array<ProdutoModelo>;
    });
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [],
      nome: ['', Validators.required],
      unidadeMedida: ['', Validators.required],
      preco: ['', [Validators.required]],
    });
  }
}
