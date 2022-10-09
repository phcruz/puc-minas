import { Component, ViewChild, OnInit, AfterViewInit, HostListener } from '@angular/core';
import { MatSidenav, MatTabGroup } from '@angular/material';
import { DrawerService } from './util/services/drawer.service';
import { DialogConfirmacaoService } from './util/services/dialog-confirmacao.service';
import { Constants } from './util/constants';
import { Router, NavigationEnd } from '@angular/router';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { UtilMetodos } from './util/util-metodos';
import { AppService } from 'src/app/util/services/app.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  title = 'pitaco-web';
  nomeUsuarioLogado: string;
  emailUsuarioLogado: string;
  avatarUsuarioLogado: string;
  realizaChamada = false;

  @ViewChild('drawer', { static: false }) public sidenav: MatSidenav;
  @ViewChild('tabsPrincipal', { static: false }) public tabsPrincipal: MatTabGroup;

  public getRouterOutletState(outlet) {
    return outlet.isActivated ? outlet.activatedRoute : '';
  }

  constructor(private sidenavService: DrawerService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private router: Router,
              private indicatorService: IndicatorBehaviorService,
              private appService: AppService) {
  }

  ngOnInit(): void {
    const path = window.location.href.toString().split(window.location.host)[1];
    const verifyPath = path.replace('/web', '');
    if (verifyPath !== '/login' && verifyPath !== '/cadastro') {
      this.nomeUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_NOME_USUARIO_LOGADO);
      this.emailUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_EMAIL_USUARIO_LOGADO);
      this.avatarUsuarioLogado = UtilMetodos.getItemSessionStorage(Constants.AVATAR_USUARIO_LOGADO);
    }

    this.ouvinteSalvaDadosUsuarioLogado();
    this.ouvinteAlteracaoUrl();
    this.verificaRolagemPagina();
  }

  ngAfterViewInit(): void {
    this.sidenavService.setSidenav(this.sidenav);
  }

  async abreTelaPerfilUsuario() {
    this.sidenavService.close();
    const idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    const promise = await this.appService.getBuscarRankingGlobalUsuarioId(idUsuarioLogado);
    if (promise !== null && promise !== undefined) {
      UtilMetodos.setItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO, JSON.stringify(promise.body));
      this.router.navigate(['tool/perfil-usuario']);
    }
  }

  abreDialogSobre() {
    this.dialogConfirmacao.openDialog(Constants.TEXTO_SOBRE, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            window.open(
              Constants.SITE_PITACO_FC,
              '_blank' // <- This is what makes it open in a new window.
            );
          }
        }
      });
    this.sidenavService.close();
  }

  navegaTabs(value: number) {
    if (value === 0) {
      this.sidenavService.close();
      this.indicatorService.setAlteraTabsViaDrawer(value);
      this.router.navigate(['app/home']);
    }
    if (value === 1) {
      this.sidenavService.close();
      this.indicatorService.setAlteraTabsViaDrawer(value);
      this.router.navigate(['app/ligas']);
    }
    if (value === 2) {
      this.sidenavService.close();
      this.indicatorService.setAlteraTabsViaDrawer(value);
      this.router.navigate(['app/grupos']);
    }
    if (value === 3) {
      this.sidenavService.close();
      this.indicatorService.setAlteraTabsViaDrawer(value);
      this.router.navigate(['app/ranking']);
    }
  }

  sairApp() {
    const mensagem = 'Deseja realmente sair?';
    this.dialogConfirmacao.openDialog(mensagem, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            UtilMetodos.clearSessionStorage();
            UtilMetodos.clearLocalStorage();
            this.router.navigate(['login']);
          }
        }
      });
    this.sidenavService.close();
  }

  ouvinteAlteracaoUrl() {
    this.router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        if (window.location.href.includes('/app/')) {
          this.indicatorService.setExibeIconeDrawermenu(true);
        } else {
          this.indicatorService.setExibeIconeDrawermenu(true); // false para nao exibir o icone
        }
      }
    });
  }

  ouvinteSalvaDadosUsuarioLogado() {
    this.indicatorService.dadosUsuarioLogado.subscribe(
      (quotes) => {
        if (quotes === Constants.SALVA_TODOS) {
          this.nomeUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_NOME_USUARIO_LOGADO);
          this.emailUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_EMAIL_USUARIO_LOGADO);
          this.avatarUsuarioLogado = UtilMetodos.getItemSessionStorage(Constants.AVATAR_USUARIO_LOGADO);
        }
        if (quotes === Constants.SALVA_AVATAR) {
          this.avatarUsuarioLogado = UtilMetodos.getItemSessionStorage(Constants.AVATAR_USUARIO_LOGADO);
        }
      }
    );
  }

  verificaRolagemPagina() {
    $(window).scroll(() => {
      if ($(window).scrollTop() + $(window).height() === $(document).height()) {
        this.realizaChamada = true;
        setTimeout(() => {
          if (this.realizaChamada) {
            const path = window.location.href.toString().split(window.location.host)[1];
            this.indicatorService.setRealizaChamadaScroll(path.replace('/web', ''));
            this.realizaChamada = false;
          }
        }, Constants.TEMPO_ESPERA_SCROLL);
      }
    });
  }

  realizaAtivacaoAppHeroku() {
    this.appService.getAtivarAppHeroku().subscribe(
      response => {
        console.log(response);
      }, err => {
        console.log(err);
      }
    );
  }

  verifyArrayRouters(url: string): boolean {
    const listaRotas: Array<string> = ['/login', '/cadastro', '/app', '/tool',
      '/app/home', '/app/ligas', '/app/grupos', '/app/ranking',
      '/tool/partidas-liga', '/tool/classificacao', '/tool/pontuadores', '/tool/detalhe-usuario',
      '/tool/perfil-usuario', '/tool/alterar-avatar', '/tool/mais-ligas', '/tool/mais-grupos',
      '/tool/detalhe-grupo', '/tool/indicar-liga', '/tool/atualizar-cadastro', '/tool/alterar-senha',
      '/tool/criar-grupo', '/tool/editar-grupo', '/tool/adicionar-amigos', '/tool/convidar-amigos',
      '/tool/gerenciar-grupo', '/tool/palpite-partida', '/tool/historico-partida', '/tool/detalhe-palpite',
      '/tool/usuario-palpite'
    ];

    const listaFilter = listaRotas.filter(item => {
      return item === url;
    });
    const isContains = listaFilter.length > 0 ? true : false;

    return isContains;
  }

  /*
  @HostListener('scroll', ['$event'])
  onScroll(event: any) {
    if ($(window).scrollTop() + $(window).height() === $(document).height()) {
      this.realizaChamada = true;
      setTimeout(() => {
        if (this.realizaChamada) {
          const path = window.location.href.toString().split(window.location.host)[1];
          this.indicatorService.setRealizaChamadaScroll(path.replace('/web', ''));
          this.realizaChamada = false;
        }
      }, Constants.TEMPO_ESPERA_SCROLL);
    }
  }
  */
}
