import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-indicar-liga',
  templateUrl: './indicar-liga.component.html',
  styleUrls: ['./indicar-liga.component.css']
})
export class IndicarLigaComponent implements OnInit {

  indicarLigaForm: FormGroup;

  constructor(private router: Router,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.indicarLigaForm = new FormGroup({
      nomeLiga: new FormControl('', [Validators.required, Validators.minLength(3)]),
      paisLiga: new FormControl('', [Validators.required, Validators.minLength(3)]),
      observacao: new FormControl(''),
    });
  }

  submit() {
    if (this.indicarLigaForm.valid) {
      const indicacaoLiga = this.preencheIndicacao();
      this.enviarCadastraIndicacaoLiga(indicacaoLiga);
    }
  }

  preencheIndicacao(): any {
    const indicacaoLiga = {
      idUsuario: UtilMetodos.getIdUsuarioTokenJWT(),
      nomeLiga: this.indicarLigaForm.get('nomeLiga').value,
      paisLiga: this.indicarLigaForm.get('paisLiga').value,
      Observacao: this.indicarLigaForm.get('observacao').value
    };

    return indicacaoLiga;
  }

  enviarCadastraIndicacaoLiga(indicacaoLiga) {
    this.appService.postCadastraIndicacaoLiga(indicacaoLiga).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.router.navigate(['tool/perfil-usuario']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
