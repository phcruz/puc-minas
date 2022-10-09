import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { HeaderAuthorizationService } from './header-authorization.service';
import { Observable, throwError } from 'rxjs';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private urlBase = `${environment.websocketBaseUrl}/partida`;
  private myWebSocket: WebSocketSubject<any> = webSocket(this.urlBase);

  constructor(private headerAuthorization: HeaderAuthorizationService) { }

  public connect(): Observable<any> {
    if (this.myWebSocket) {
      return this.myWebSocket;
    } else {
      this.myWebSocket = webSocket(this.urlBase);
      return this.myWebSocket;
    }
  }

  public sendMessage(message: any) {
    if (this.myWebSocket) {
      this.myWebSocket.next(message);
    } else {
      console.error('Did not send data, open a connection first');
    }
  }

  public closeConnection() {
    if (this.myWebSocket) {
      this.myWebSocket.complete();
      this.myWebSocket = null;
    }
  }

  handleError(err: HttpErrorResponse) {
    return throwError(err);
  }
}
