import { Injectable } from '@angular/core';
import { DialogMensagemErroComponent } from 'src/app/dialogs/dialog-mensagem-erro/dialog-mensagem-erro.component';
import { MatDialogRef, MatDialog } from '@angular/material';
import { Observable } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import * as HttpStatusCode from 'http-status-codes';
import { Router } from '@angular/router';
import { v4 as uuidv4 } from 'uuid';
import { UtilMetodos } from '../util-metodos';

@Injectable({
  providedIn: 'root'
})
export class DialogMensagemErroService {

  private dialogRef: MatDialogRef<DialogMensagemErroComponent>;

  constructor(private dialog: MatDialog,
              private router: Router) { }

  public openDialog(title: string, information: any) {
    this.dialogRef = this.dialog.open(DialogMensagemErroComponent, {
      width: '300px',
      data: { titulo: title, informacao: information },
      disableClose: true
    });
  }

  public confirmed(): Observable<any> {
    return this.dialogRef.afterClosed().pipe(take(1), map(res => {
      return res;
    }));
  }

  public showMensagemErroDialog(err: HttpErrorResponse) {
    const title = 'Erro';

    if (err.status === HttpStatusCode.UNAUTHORIZED) {
      UtilMetodos.clearSessionStorage();
      const information = {
        message: 'Message: ' + 'Problemas com a autenticação. Faça login novamente.',
        uniqueId: 'UniqueId: ' + err.error.uniqueId,
        time: 'Time: ' + err.error.time,
        status: err.status + ' - ' + err.statusText
      };

      this.openDialog(title, information);
      this.confirmed().subscribe(
        res => {
          this.router.navigate(['login']);
        }
      );
    } else {
      let information = {};
      if (err.error === null || err.error === undefined) {
        information = {
          message: 'Message: ' + 'OPS!  😢. Tivemos um problema ao carregar página. Por favor tente novamente.',
          uniqueId: 'UniqueId: ' + uuidv4(),
          time: 'Time: ' + new Date().getTime(),
          status: err.status + ' - ' + err.statusText
        };
      } else {
        information = {
          message: 'Message: ' + (err.error.message === undefined
                              ? 'OPS!  😢. Tivemos um problema ao carregar página. Por favor tente novamente.' : err.error.message),
          uniqueId: 'UniqueId: ' + (err.error.uniqueId === undefined ? uuidv4() : err.error.uniqueId),
          time: 'Time: ' + (err.error.time === undefined ? new Date().getTime() : err.error.time),
          status: err.status + ' - ' + err.statusText
        };
      }

      this.openDialog(title, information);
      this.confirmed().subscribe(
        res => {
          console.log('apenas fecha a tela');
        }
      );
    }
  }
}
