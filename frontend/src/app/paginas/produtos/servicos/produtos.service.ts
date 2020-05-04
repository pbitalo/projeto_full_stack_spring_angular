import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { ProdutoModelo } from '../modelo/produto.model';

@Injectable({
  providedIn: 'root',
})
export class ProdutosService extends RecursoPadraoServico<ProdutoModelo> {
  constructor(protected injector: Injector) {
    super('produto', injector);
  }
}
