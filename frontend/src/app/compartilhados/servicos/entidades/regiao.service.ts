import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';

@Injectable({
  providedIn: 'root',
})
export class RegiaoService extends RecursoPadraoServico<any> {
  constructor(protected injector: Injector) {
    super('regiao', injector);
  }
}
