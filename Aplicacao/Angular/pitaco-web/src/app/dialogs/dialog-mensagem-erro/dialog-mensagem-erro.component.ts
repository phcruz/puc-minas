import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-mensagem-erro',
  templateUrl: './dialog-mensagem-erro.component.html',
  styleUrls: ['./dialog-mensagem-erro.component.css']
})
export class DialogMensagemErroComponent {

  constructor(public dialogRef: MatDialogRef<DialogMensagemErroComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
