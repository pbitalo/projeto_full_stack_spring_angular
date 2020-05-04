import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, take, catchError, retry } from 'rxjs/operators';
import { Injector } from '@angular/core';

import { environment } from '../../../environments/environment';
import { RecursoPadraoModelo } from '../modelos/recurso-padrao.model';
import { HttpUtilService } from './http-util.service';

export abstract class RecursoPadraoServico<T extends RecursoPadraoModelo> {
  readonly url = environment.apiUrl;

  protected http: HttpClient;

  protected httpUtil: HttpUtilService;

  _path = '';

  constructor(protected apiPath: string, protected injector: Injector) {
    this._path = apiPath;
    this.http = injector.get(HttpClient);
    this.httpUtil = injector.get(HttpUtilService);
  }

  get urlCompleta() {
    return `${this.url}/${this._path}`;
  }

  salvar(params: any): Observable<T> {
    return params.id ? this.atualizar(params) : this.criar(params);
  }

  criar(params?: any): Observable<T> {
    return this.http
      .post<any>(`${this.urlCompleta}`, params)
      .pipe(map(() => params));
  }

  atualizar(params: any): Observable<T> {
    return this.http
      .put(`${this.urlCompleta}/${params.id}`, params)
      .pipe(map(() => params));
  }

  deletar(id: number): Observable<T> {
    return this.http
      .delete<T>(`${this.urlCompleta}/${id}`)
      .pipe(retry(1), catchError(this.handleError));
  }

  pegarRecursoAlternativo(params: any, path: string) {
    return this.pegarDados(params, path);
  }

  pegarDados(params?: any, path?: string): Observable<T> {
    return this.http
      .get<T>(path ? `${this.url}/${path}` : `${this.urlCompleta}`, {
        params: this.httpUtil.httpParamsByObjeto(params),
      })
      .pipe(retry(1), catchError(this.handleError));
  }

  pegarDadosAlternativosById(
    id: any,
    path: string,
    params?: any
  ): Observable<T> {
    return this.http
      .get<T>(`${this.urlCompleta}/${path}${id}`, {
        params: this.httpUtil.httpParamsByObjeto(params),
      })
      .pipe(retry(1), catchError(this.handleError));
  }

  pegarPorId(id: number): Observable<T> {
    return this.http
      .get<T>(`${this.urlCompleta}${id}`)
      .pipe(retry(1), catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      if (error.status === 404) {
        alert('Não existe dados na base para esse filtro');
      } else {
        alert(
          'Houve um erro na requisição. Este registro não pode ser apagado pq existe associações.'
        );
      }
    }
    return throwError('Something bad happened; please try again later.');
  }
}
