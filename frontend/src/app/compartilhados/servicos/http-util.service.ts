import { Injectable } from '@angular/core';

@Injectable()
export class HttpUtilService {
  httpParamsByObjeto(
    objeto: object
  ): {
    [param: string]: string | string[];
  } {
    const httpParams: {
      [param: string]: string | string[];
    } = {};
    if (objeto != null) {
      for (const key in objeto) {
        const parametro = objeto[key];
        if (
          parametro !== null &&
          parametro !== 'null' &&
          parametro !== 'undefined' &&
          parametro !== undefined
        ) {
          if (String(parametro).trim()) {
            httpParams[key] = String(objeto[key]);
          }
        }
      }
    }
    return httpParams;
  }
}
