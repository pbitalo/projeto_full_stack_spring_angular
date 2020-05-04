import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { HistoricoModelo } from '../modelo/historico.model';

@Injectable({
  providedIn: 'root',
})
export class HistoricoService extends RecursoPadraoServico<HistoricoModelo> {
  constructor(protected injector: Injector) {
    super('historicoPreco', injector);
  }
}
