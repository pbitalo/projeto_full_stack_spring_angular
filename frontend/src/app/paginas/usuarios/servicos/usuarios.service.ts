import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { UsuarioModelo } from '../modelo/usuario.model';

@Injectable({
  providedIn: 'root',
})
export class UsuariosService extends RecursoPadraoServico<UsuarioModelo> {
  constructor(protected injector: Injector) {
    super('user', injector);
  }
}
