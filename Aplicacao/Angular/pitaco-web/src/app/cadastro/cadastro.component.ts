import { Component, OnInit } from '@angular/core';
import { trigger, state, transition, style, animate } from '@angular/animations';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import * as $ from 'jquery';
import { UtilMetodos } from '../util/util-metodos';
import { Constants } from '../util/constants';
import { stringify } from 'querystring';
import { Router } from '@angular/router';
import { ToastService } from '../util/services/toast.service';
import { AppService } from '../util/services/app.service';
import { DialogMensagemErroService } from '../util/services/dialog-mensagem-erro.service';
import { DialogConfirmacaoService } from '../util/services/dialog-confirmacao.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css'],
  animations: [
    trigger('Fading', [
      state('void', style({ opacity: 0 })), transition(':enter', animate('1.5s ease-out')),
    ])
  ]
})
export class CadastroComponent implements OnInit {

  urlPlanoFundo = '';
  cadastroForm: FormGroup;

  constructor(private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService) { }

  ngOnInit() {
    this.getPlanoFundo();

    this.cadastroForm = new FormGroup({
      nome: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12),
                                  Validators.pattern('^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$')]),
      senhaConfirmacao: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12),
                                  Validators.pattern('^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$')])
    });
  }

  submit() {
    if (this.cadastroForm.valid) {
      const senha = this.cadastroForm.get('senha').value;
      const senhaConfirmacao = this.cadastroForm.get('senhaConfirmacao').value;
      if (senha === senhaConfirmacao) {
        const emailCad = this.cadastroForm.get('email').value;
        this.buscarEmail(emailCad);
      } else {
        this.toastService.showToast('As senhas digitadas nao conferem. Verifique-as por favor.');
      }
    }
  }

  buscarEmail(email: string) {
    this.appService.buscaUsuarioEmail(email).subscribe(
      response => {
        this.verificaUsuario(response.body);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  verificaUsuario(data: any) {
    if (data === null || data === undefined) {
      const user = this.criaObjetoUsuario(Constants.APLICATIVO_WEB);
      this.cadastraUsuarioWebService(user);
    } else {
      this.toastService.showToast('E-mail já cadastrado.');
    }
  }

  cadastraUsuarioWebService(usuario) {
    this.appService.postCadastraUsuario(usuario).subscribe(
      response => {
        this.exibeMensagemCadastroSucessoInativo();
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  criaObjetoUsuario(localCadastroWeb): any {
    const nomeCad = this.cadastroForm.get('nome').value;
    const emailCad = this.cadastroForm.get('email').value;
    const senhaCripto = UtilMetodos.criptografaSenha(this.cadastroForm.get('senha').value);

    let nomeUsuario;
    if (stringify(nomeCad).length > Constants.TAMANHO_MAX_NOME) {
        nomeUsuario = stringify(nomeCad).substring(0, Constants.TAMANHO_MAX_NOME);
    } else {
        nomeUsuario = nomeCad;
    }

    const user = {
      nome: nomeUsuario,
      email: emailCad,
      senha: senhaCripto,
      telefone: '',
      ativo: false,
      localCadastro: localCadastroWeb
    };

    return user;
  }

  exibeMensagemCadastroSucessoInativo() {
    this.dialogConfirmacao.openDialog(Constants.TEXTO_ATIVACAO_CADASTRO, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        UtilMetodos.clearSessionStorage();
        this.router.navigate(['login']);
      }
    );
  }

  getPlanoFundo() {
    this.appService.getPlanoFundo().subscribe(
      response => {
        this.urlPlanoFundo = response.body.urlImagem;
        $('app-cadastro').css('background-image', 'url(' + this.urlPlanoFundo + ')');
        $('app-cadastro').css('background-size', 'cover');
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  moverPainel(idPainel) {
    $(idPainel).on('mousedown', function(e) {
      $(this).addClass('active');
      var oTop = e.pageY - $('.active').offset().top;
      var oLeft = e.pageX - $('.active').offset().left;
      $(this).parents().on('mousemove', function(e) {
          $('.active').offset({
              top: e.pageY - oTop,
              left: e.pageX - oLeft,
              position: 'relative'
          });
       });
      $(this).on('mouseup', function () {
        $(this).removeClass('active');
      });
      return false;
    });
  }
}
