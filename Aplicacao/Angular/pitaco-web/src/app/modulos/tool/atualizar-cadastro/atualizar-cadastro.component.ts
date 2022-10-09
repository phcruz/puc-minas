import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { ToastService } from 'src/app/util/services/toast.service';
import { Router } from '@angular/router';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/util/date-adapter';

@Component({
  selector: 'app-atualizar-cadastro',
  templateUrl: './atualizar-cadastro.component.html',
  styleUrls: ['./atualizar-cadastro.component.css'],
  providers: [
    { provide: DateAdapter, useClass: AppDateAdapter },
    { provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS }
  ]
})
export class AtualizarCadastroComponent implements OnInit {

  atualizarCadastroForm: FormGroup;

  constructor(private toastService: ToastService,
              private router: Router,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private indicatorService: IndicatorBehaviorService) { }

  ngOnInit() {
    this.atualizarCadastroForm = new FormGroup({
      nomeCompleto: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      timeCoracao: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]),
      telefone: new FormControl('', [Validators.maxLength(15)]),
      dataNascimento: new FormControl('', [Validators.required, Validators.minLength(10)]),
    });
  }

  submit() {
    if (this.atualizarCadastroForm.valid) {
      if (this.verificaDataNascimento()) {
        const usuarioAtualiza = this.preencheUsuario();
        this.enviaAtualizacaoUsuario(usuarioAtualiza);
      } else {
        const mensagem = 'OPS. A idade mínima para jogar é de 6 anos.';
        this.toastService.showToast(mensagem);
      }
    }
  }

  verificaDataNascimento(): boolean {
    const hoje = new Date();
    const stringIdadeMinima = (hoje.getMonth() + 1) + '-'
                            + hoje.getDate() + '-'
                            + (hoje.getFullYear() + Constants.QUANTIDADE_ANOS_IDADE_MINIMA);

    const idadeMinima = new Date(stringIdadeMinima);
    const idadeSelecionada = this.atualizarCadastroForm.get('dataNascimento').value;

    let expirou = false;

    if (idadeMinima.getTime() >= idadeSelecionada.getTime()) {
      expirou = true;
    }

    return expirou;
  }

  preencheUsuario(): any {
    const dataNasc = this.atualizarCadastroForm.get('dataNascimento').value;
    const stringIdadeMinima = dataNasc.getDate() + '/' + (dataNasc.getMonth() + 1) + '/' + dataNasc.getFullYear() + ' 03:00:00';
    const usuarioAtualiza = {
      idUsuario: UtilMetodos.getIdUsuarioTokenJWT(),
      nome: this.atualizarCadastroForm.get('nomeCompleto').value,
      timeCoracao: this.atualizarCadastroForm.get('timeCoracao').value,
      telefone: this.atualizarCadastroForm.get('telefone').value,
      dataNascimento: stringIdadeMinima,
    };

    return usuarioAtualiza;
  }

  enviaAtualizacaoUsuario(usuarioAtualiza: any) {
    this.appService.putAtualizarUsuario(usuarioAtualiza).subscribe(
      response => {
        if (response.headers.get(Constants.AUTHORIZATION)) {
          UtilMetodos.setItemSessionStorage(Constants.AUTHORIZATION, response.headers.get(Constants.AUTHORIZATION));
        }

        this.toastService.showToast(response.body);
        this.alteraNomeRanking(usuarioAtualiza.nome);
        this.router.navigate(['tool/perfil-usuario']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  alteraNomeRanking(nomeUsuario: string) {
    const userRanking = UtilMetodos.getItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO);
    if (userRanking) {
      const user = JSON.parse(userRanking);
      user.nomeUsuario = nomeUsuario;
      UtilMetodos.setItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO, JSON.stringify(user));
      this.salvaDadosUsuarioSession(nomeUsuario);
    }
  }

  salvaDadosUsuarioSession(nomeUsuario: string) {
    const infosUser = JSON.parse(UtilMetodos.getItemSessionStorage(Constants.INFOS_USUARIO_LOGADO));
    infosUser.nome = nomeUsuario;
    UtilMetodos.setItemSessionStorage(Constants.INFOS_USUARIO_LOGADO, JSON.stringify(infosUser));

    this.indicatorService.setSalvaDadosUsuarioLogado(Constants.SALVA_TODOS);
  }
}
