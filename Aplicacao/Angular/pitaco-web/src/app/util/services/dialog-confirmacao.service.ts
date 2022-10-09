import { Injectable } from '@angular/core';
import { DialogConfirmacaoComponent } from 'src/app/dialogs/dialog-confirmacao/dialog-confirmacao.component';
import { MatDialogRef, MatDialog } from '@angular/material';
import { Observable } from 'rxjs';
import { take, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DialogConfirmacaoService {

  private dialogRef: MatDialogRef<DialogConfirmacaoComponent>;

  constructor(private dialog: MatDialog) { }

  public openDialog(information: string, description: string) {
    this.dialogRef = this.dialog.open(DialogConfirmacaoComponent, {
      width: '300px',
      data: { informacao: information, descricao: description }
    });
  }

  public confirmed(): Observable<any> {
    return this.dialogRef.afterClosed().pipe(take(1), map(res => {
      return res;
    }));
  }

}
