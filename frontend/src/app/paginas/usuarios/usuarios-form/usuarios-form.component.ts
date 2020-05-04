import { Component, Injector } from '@angular/core';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { Validators, FormBuilder } from '@angular/forms';
import { UsuariosService } from '../servicos/usuarios.service';
import { UsuarioModelo } from '../modelo/usuario.model';

@Component({
  selector: 'app-usuarios-form',
  templateUrl: './usuarios-form.component.html',
  styleUrls: ['./usuarios-form.component.css'],
})
export class UsuariosFormComponent extends CrudComponent {
  items: UsuarioModelo[];

  constructor(
    protected servicos: UsuariosService,
    protected injector: Injector,
    private formBuilder: FormBuilder
  ) {
    super(servicos, injector);
  }

  pegarDados() {
    this.servicos.pegarDados().subscribe((result) => {
      this.items = result as Array<UsuarioModelo>;
    });
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      id: [],
      password: ['', [Validators.required, Validators.minLength(6)]],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }
}
