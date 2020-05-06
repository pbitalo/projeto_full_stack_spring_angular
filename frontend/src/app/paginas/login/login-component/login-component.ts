import { Component, Injector } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { CrudComponent } from 'src/app/compartilhados/componente-padrao/crud-component';
import { UsuariosService } from '../../usuarios/servicos/usuarios.service';
import { UsuarioModelo } from '../../usuarios/modelo/usuario.model';

@Component({
  selector: 'app-login',
  templateUrl: './login-component.html',
  styleUrls: ['./login-component.css'],
})
export class LoginComponent extends CrudComponent {
  credenciais = '';
  items: UsuarioModelo;
  constructor(
    private formBuilder: FormBuilder,
    protected servicos: UsuariosService,
    protected injector: Injector
  ) {
    super(servicos, injector);
  }

  login() {
    if (this.validarDados()) {
      this.autenticarUsuario();
    }
  }

  criarFormulario() {
    this.formulario = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      name: ['', [Validators.required]],
    });
  }

  pegarDados() {
    this.servicos.pegarDados().subscribe((result) => {
      this.items = result.data.content as any;
    });
  }

  autenticarUsuario() {
    const usu = this.formulario.getRawValue();
    for (const i in this.items) {
      if (
        this.items[i].name === usu.name &&
        this.items[i].password === usu.password
      ) {
        this.credenciais = 'ok';
        this.router.navigateByUrl('/usuarios', {
          state: { estaLogado: true },
        });
      }
    }
    if (this.credenciais === '') {
      this.credenciais = 'falhou';
    }
  }
}
