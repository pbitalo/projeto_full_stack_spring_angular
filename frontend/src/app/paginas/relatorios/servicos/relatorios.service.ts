import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { RelatoriosModelo } from '../modelo/relatorios.model';

@Injectable({
  providedIn: 'root',
})
export class RelatoriosService extends RecursoPadraoServico<RelatoriosModelo> {
  constructor(protected injector: Injector) {
    super('relatorios', injector);
  }
}
