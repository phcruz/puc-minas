import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { ToastService } from 'src/app/util/services/toast.service';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-convidar-amigos',
  templateUrl: './convidar-amigos.component.html',
  styleUrls: ['./convidar-amigos.component.css']
})
export class ConvidarAmigosComponent implements OnInit {

  convidarAmigosForm: FormGroup;

  constructor(private router: Router,
              private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.convidarAmigosForm = new FormGroup({
      emailAmigo: new FormControl('', [Validators.required, Validators.email, Validators.maxLength(40)]),
    });
  }

  submit() {
    if (this.convidarAmigosForm.valid) {
      this.enviaConviteAmigo();
    }
  }

  enviaConviteAmigo() {
    const email = this.convidarAmigosForm.get('emailAmigo').value;
    const nomeUsuario = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_NOME_USUARIO_LOGADO);

    this.appService.getEnviaConviteAmigo(email, nomeUsuario).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.router.navigate(['tool/detalhe-grupo']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
