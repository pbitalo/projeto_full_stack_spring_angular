import { Injectable, Injector } from '@angular/core';
import { RecursoPadraoServico } from 'src/app/compartilhados/servicos/recurso-padrao.service';
import { take } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MunicipioService extends RecursoPadraoServico<any> {
  constructor(protected injector: Injector) {
    super('municipio', injector);
  }

  //gambis (sem tempo)
  pegarMediaPrecoCompra(params?: any): Observable<any> {
    return this.http
      .get('http://localhost:8080//municipio/buscarMediaPrecoCompra', {
        params: this.httpUtil.httpParamsByObjeto(params),
      })
      .pipe(take(1));
  }
}
