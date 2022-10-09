import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { MatDialogRef, MatDialog } from '@angular/material';
import { DialogMensagemComponent } from 'src/app/dialogs/dialog-mensagem/dialog-mensagem.component';

@Injectable({
  providedIn: 'root'
})
export class DialogMensagemService {

  private dialogRef: MatDialogRef<DialogMensagemComponent>;

  constructor(private dialog: MatDialog) { }

  public openDialog(title: string, information: string, description: string) {
    this.dialogRef = this.dialog.open(DialogMensagemComponent, {
      width: '300px',
      data: { titulo: title, informacao: information, descricao: description }
    });
  }

  public confirmed(): Observable<any> {
    return this.dialogRef.afterClosed().pipe(take(1), map(res => {
      return res;
    }));
  }

}
