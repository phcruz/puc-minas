import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Constants } from '../constants';
import { UtilMetodos } from '../util-metodos';

@Injectable({
  providedIn: 'root'
})
export class HeaderAuthorizationService {

  tokenJwt = '';

  constructor() { }

  createHeaders(): HttpHeaders {
    this.tokenJwt = UtilMetodos.getItemSessionStorage(Constants.AUTHORIZATION);
    if (this.tokenJwt === null || this.tokenJwt === undefined) {
      this.tokenJwt = 'token';
    }
    const correlationId = UtilMetodos.getItemSessionStorage(Constants.CORRELATION_ID);
    const registerPublic = btoa(Constants.SECRET_REGISTER_PUBLIC + UtilMetodos.createDateTimeString());

    const headersJwt = new HttpHeaders()
                      .append(Constants.AUTHORIZATION, this.tokenJwt)
                      .append(Constants.HEADER_REGISTER_PUBLIC, registerPublic)
                      .append(Constants.CORRELATION_ID, correlationId);
    return headersJwt;
  }

  createHeadersPublic(): HttpHeaders {
    const registerPublic = btoa(Constants.SECRET_REGISTER_PUBLIC + UtilMetodos.createDateTimeString());
    const headersRegisterPublic = new HttpHeaders().append(Constants.HEADER_REGISTER_PUBLIC, registerPublic);
    return headersRegisterPublic;
  }
}
