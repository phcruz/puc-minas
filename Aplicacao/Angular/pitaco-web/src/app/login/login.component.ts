import { DeviceDetectorService } from 'ngx-device-detector';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { trigger, transition, style, state, animate } from '@angular/animations';
import * as $ from 'jquery';
import { AppService } from '../util/services/app.service';
import { UtilMetodos } from '../util/util-metodos';
import { Constants } from '../util/constants';
import { ToastService } from '../util/services/toast.service';
import { DialogMensagemService } from '../util/services/dialog-mensagem.service';
import { Router } from '@angular/router';
import { DialogMensagemErroService } from '../util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from '../util/services/indicator-behavior.service';
import { AuthService } from 'angularx-social-login';
import { FacebookLoginProvider, GoogleLoginProvider } from 'angularx-social-login';
import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material';
import { stringify } from 'querystring';
import { DialogConfirmacaoService } from '../util/services/dialog-confirmacao.service';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('Fading', [
      state('void', style({ opacity: 0 })), transition(':enter', animate('1.5s ease-out')),
    ])
  ]
})
export class LoginComponent implements OnInit {

  googleLogoURL = 'https://raw.githubusercontent.com/fireflysemantics/logo/master/Google.svg';
  loginForm: FormGroup;
  user: any;
  loggedIn: boolean;
  socialAuthService: any;
  urlPlanoFundo = '';
  hide = true;

  constructor(private appService: AppService,
              private toastService: ToastService,
              private router: Router,
              private authService: AuthService,
              private dialogMensagemService: DialogMensagemService,
              private dialogErroService: DialogMensagemErroService,
              private indicatorService: IndicatorBehaviorService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private matIconRegistry: MatIconRegistry,
              private domSanitizer: DomSanitizer,
              private deviceService: DeviceDetectorService) {
    this.matIconRegistry.addSvgIcon('logo_google', this.domSanitizer.bypassSecurityTrustResourceUrl(this.googleLogoURL));
  }

  ngOnInit() {
    this.obtemInfosDispositivo();
    this.getPlanoFundo();

    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12),
                                  Validators.pattern('^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$')]),
    });
  }

  submit() {
    if (this.loginForm.valid) {
      const email = this.loginForm.get('email').value;

      this.buscarEmail(email);
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
      this.toastService.showToast('E-mail não cadastrado. Clique abaixo e cadastre-se.');
    } else {
      const senhaCripto = UtilMetodos.criptografaSenha(this.loginForm.get('senha').value);
      if (data.senha === senhaCripto) {
        if (data.ativo) {
          this.realizaLogin(data);
        } else {
          this.exibeAlertDialogLogin();
        }
      } else {
        this.toastService.showToast('Usuario/senha incorreta.');
      }
    }
  }

  realizaLogin(usuario: any) {
    this.appService.postLoginUsuario(usuario).subscribe(
      response => {
        this.salvaDadosUsuarioSession(response.body);
        UtilMetodos.setItemSessionStorage(Constants.AUTHORIZATION, response.headers.get(Constants.AUTHORIZATION));
        UtilMetodos.setItemSessionStorage(Constants.CORRELATION_ID, response.headers.get(Constants.CORRELATION_ID));

        this.controleAcessoPlataforma();
        this.router.navigate(['app/home']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  exibeAlertDialogLogin() {
    const title = 'Erro';
    const information = 'OPS!  😢 \n'
    + 'Parece que sua conta está inativa. Verifique seu e-mail e realize a validação da sua conta para liberar o seu acesso.'
    + 'Em caso de dúvidas entre em contato com nossa equipe: contato@pitacofc.com.br';
    this.dialogMensagemService.openDialog(title, information, '');
  }

  signOut(): void {
    this.authService.signOut();
  }

  socialLogin(socialPlatform: string): void {
    let socialPlatformProvider;
    if (socialPlatform === 'Facebook') {
      socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
    } else if (socialPlatform === 'Google') {
      socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;
    }

    this.authService.signIn(socialPlatformProvider).then(
      (userData) => {
        this.user = userData;
        this.loggedIn = (userData != null);

        if (socialPlatformProvider === GoogleLoginProvider.PROVIDER_ID) {
          this.buscaEmailUsuarioGoogle();
        }
        if (socialPlatformProvider === FacebookLoginProvider.PROVIDER_ID) {
          this.buscaEmailUsuarioFacebook();
        }
      }
    );
  }

  buscaEmailUsuarioGoogle() {
    this.appService.buscaUsuarioEmail(this.user.email).subscribe(
      response => {
        this.verificaUsuarioGoogle(response.body);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  verificaUsuarioGoogle(data: any) {
    if (data === null || data === undefined) {
      const user = this.criaObjetoUsuario(Constants.GOOGLE);
      this.cadastraUsuarioWebService(user);
    } else {
      this.realizaLogin(data);
    }
  }

  buscaEmailUsuarioFacebook() {
    this.appService.buscaUsuarioEmail(this.user.email).subscribe(
      response => {
        this.verificaUsuarioFacebook(response.body);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  verificaUsuarioFacebook(data: any) {
    if (data === null || data === undefined) {
      const user = this.criaObjetoUsuario(Constants.FACEBOOK);
      this.cadastraUsuarioWebService(user);
    } else {
      this.realizaLogin(data);
    }
  }

  cadastraUsuarioWebService(usuario) {
    this.appService.postCadastraUsuario(usuario).subscribe(
      response => {
        this.salvaDadosUsuarioSession(response.body);
        UtilMetodos.setItemSessionStorage(Constants.AUTHORIZATION, response.headers.get(Constants.AUTHORIZATION));

        this.controleAcessoPlataforma();
        this.router.navigate(['app/home']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  criaObjetoUsuario(localCadastroWeb): any {
    let nomeUsuario;
    if (stringify(this.user.name).length > Constants.TAMANHO_MAX_NOME) {
        nomeUsuario = stringify(this.user.name).substring(0, Constants.TAMANHO_MAX_NOME);
    } else {
        nomeUsuario = this.user.name;
    }

    const user = {
      nome: nomeUsuario,
      email: this.user.email,
      senha: UtilMetodos.criptografaSenha('Google123'),
      telefone: '',
      ativo: true,
      localCadastro: localCadastroWeb
    };

    return user;
  }

  recuperacaoSenhaUsuario() {
    if (this.loginForm.get('email').valid) {
      const emailCad = this.loginForm.get('email').value;
      this.recuperarSenhaUsuario(emailCad);
    } else {
      this.exibeMensagemPreenchecampo();
    }
  }

  exibeMensagemPreenchecampo() {
    const texto = 'Para recuperar a sua senha, preencha o campo "E-mail" com seu e-mail cadastrado e clique em "Recuperar minha senha"';
    this.dialogConfirmacao.openDialog(texto, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        $('#email-login').focus();
      }
    );
  }

  recuperarSenhaUsuario(email) {
    this.appService.getRecuperaSenhaUsuario(email).subscribe(
      response => {
        this.toastService.showToast(response.body);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  getPlanoFundo() {
    this.appService.getPlanoFundo().subscribe(
      response => {
        this.urlPlanoFundo = response.body.urlImagem;
        $('app-login').css('background-image', 'url(' + this.urlPlanoFundo + ')');
        $('app-login').css('background-size', 'cover');
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

  controleAcessoPlataforma() {
    const deviceInfo = this.obtemInfosDispositivo();
    this.appService.postAcessoPlataforma(deviceInfo).subscribe(
      response => {
        // console.log(response);
      },
      err => {
        // console.log(err);
      }
    );
  }

  obtemInfosDispositivo(): any {
    const deviceInfo = this.deviceService.getDeviceInfo();
    let objeto: any;

    if (this.deviceService.isDesktop()) {
      return objeto = {
        sistema: deviceInfo.os,
        dispositivo: deviceInfo.os_version,
        descricao: deviceInfo.userAgent,
        detalhe: deviceInfo.browser + ' - ' + deviceInfo.browser_version,
      };
    } else {
      return objeto = {
        sistema: deviceInfo.os,
        dispositivo: deviceInfo.device,
        descricao: deviceInfo.userAgent,
        detalhe: deviceInfo.browser + ' - ' + deviceInfo.browser_version
      };
    }
  }

  salvaDadosUsuarioSession(usuario: any) {
    const infosUser = {
      nome: usuario.nome,
      email: usuario.email,
      transaction: uuidv4() + '#' + usuario.idUsuario
    };
    UtilMetodos.setItemSessionStorage(Constants.INFOS_USUARIO_LOGADO, JSON.stringify(infosUser));
    UtilMetodos.setItemSessionStorage(Constants.AVATAR_USUARIO_LOGADO, usuario.avatar.urlImagem);

    this.indicatorService.setSalvaDadosUsuarioLogado(Constants.SALVA_TODOS);
  }
}
