import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { ClienteModelo } from '../modelo/cliente.model';

@Injectable({
  providedIn: 'root',
})
export class ClientesService extends RecursoPadraoServico<ClienteModelo> {
  constructor(protected injector: Injector) {
    super('cliente', injector);
  }
}
