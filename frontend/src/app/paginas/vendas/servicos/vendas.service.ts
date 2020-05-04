import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { VendasModelo } from '../modelo/vendas.model';

@Injectable({
  providedIn: 'root',
})
export class VendasService extends RecursoPadraoServico<VendasModelo> {
  constructor(protected injector: Injector) {
    super('venda', injector);
  }
}
