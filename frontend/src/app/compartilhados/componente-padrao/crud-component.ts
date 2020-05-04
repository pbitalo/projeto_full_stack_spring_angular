import { OnInit, Injector } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

export abstract class CrudComponent implements OnInit {
  formulario: FormGroup;
  submeter = false;
  activatedRoute: ActivatedRoute;
  router: Router;
  config = {
    itemsPerPage: 10,
    currentPage: 1,
    totalItems: null,
  };

  constructor(protected servico, injector: Injector) {
    this.activatedRoute = injector.get(ActivatedRoute);
    this.router = injector.get(Router);
  }

  ngOnInit(): void {
    this.criarFormulario();
    this.pegarDados();
  }

  abstract criarFormulario();

  abstract pegarDados();

  validarDados() {
    this.submeter = true;

    if (this.formulario.invalid) {
      return;
    }

    return true;
  }

  persistirDados() {
    if (this.validarDados()) {
      this.requisitarPersistencia();
    }
  }

  requisitarPersistencia() {
    this.servico.salvar(this.pegarModelo).subscribe((result) => {
      this.pegarDados();
    });
  }

  excluirItem(item: any) {
    this.servico.deletar(item.id).subscribe(() => {
      this.pegarDados();
    });
  }

  editarItem(item: any) {
    this.formulario.patchValue(item);
  }

  limpar() {
    this.submeter = false;
    this.formulario.reset();
  }

  get pegarModelo() {
    return this.formulario.getRawValue().id
      ? this.modeloPut()
      : this.modeloPost();
  }

  protected modeloPost() {
    const model = this.formulario.getRawValue();
    delete model.id;
    return model;
  }

  protected modeloPut() {
    return this.formulario.getRawValue();
  }

  protected get id(): number {
    return this.activatedRoute.snapshot.params.id;
  }
}
