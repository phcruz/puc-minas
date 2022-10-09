import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IndicatorBehaviorService } from '../util/services/indicator-behavior.service';
import { AppService } from '../util/services/app.service';
import { DrawerService } from '../util/services/drawer.service';
import { UtilMetodos } from '../util/util-metodos';
import { Constants } from '../util/constants';
import { DialogMensagemErroService } from '../util/services/dialog-mensagem-erro.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  exibeIconeClassificacao = false;
  exibeIconeInfoLigas = false;
  exibeIconePontuadores = false;
  exibeIconeDrawermenu = false;
  exibeIconePerfilUsuario = false;
  exibeIconeGrupoDetalhe = false;
  exibeIconeMaisGrupos = false;
  exibeIconeDetalhesPalpites = false;
  ligasAtivas = [];
  itensMenuGrupo = {
    adicionarAmigos: false,
    convidarAmigos: false,
    editarGrupo: false,
    gerenciarGrupo: false
  };

  constructor(private router: Router,
              private indicatorService: IndicatorBehaviorService,
              private appService: AppService,
              private drawerMenu: DrawerService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.ouvinteExibicaoIconeClassificacao();
    this.ouvinteExibicaoIconeInfoLigas();
    this.ouvinteExibicaoIconePontuadores();
    this.ouvinteAlteracaoUrl();
    this.ouvinteExibicaoIconePerfilUsuario();
    this.ouvinteExibicaoIconeGrupoDetalhe();
    this.ouvinteExibicaoIconeMaisGrupos();
    this.ouvinteExibicaoIconeDetalhesPalpites();
  }

  ouvinteExibicaoIconeClassificacao() {
    this.indicatorService.exibeIconeClassificacaoGeral.subscribe(
      (quotes) => {
        this.exibeIconeClassificacao = quotes;
      }
    );
  }

  ouvinteExibicaoIconePerfilUsuario() {
    this.indicatorService.exibeIconePerfilUsuario.subscribe(
      (quotes) => {
        this.exibeIconePerfilUsuario = quotes;
      }
    );
  }

  ouvinteExibicaoIconePontuadores() {
    this.indicatorService.exibeIconePontuadores.subscribe(
      (quotes) => {
        this.exibeIconePontuadores = quotes;
      }
    );
  }

  ouvinteExibicaoIconeGrupoDetalhe() {
    this.indicatorService.exibeIconeGrupoDetalhe.subscribe(
      (quotes) => {
        this.exibeIconeGrupoDetalhe = quotes;
        this.verificaItensMenuGrupo();
      }
    );
  }

  ouvinteAlteracaoUrl() {
    this.indicatorService.exibeIconeDrawermenu.subscribe(
      (quotes) => {
        this.exibeIconeDrawermenu = quotes;
      }
    );
  }

  ouvinteExibicaoIconeInfoLigas() {
    this.indicatorService.exibeIconeInfoLigas.subscribe(
      (quotes) => {
        if (quotes === true) {
          this.getLigasAtivasUsuario();
        }
        this.exibeIconeInfoLigas = quotes;
      }
    );
  }

  abreClassificacao() {
    this.indicatorService.setExibeIconeClassificacaoGeral(false);
    this.router.navigate(['tool/classificacao']);
  }

  abreArtilharia() {
    this.indicatorService.setExibeIconeClassificacaoGeral(false);
    this.router.navigate(['tool/artilharia']);
  }

  abrePontuadores() {
    this.indicatorService.setExibeIconePontuadores(false);
    this.router.navigate(['tool/pontuadores']);
  }

  getLigasAtivasUsuario() {
    const idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (idUsuarioLogado != null && idUsuarioLogado !== undefined) {
      this.appService.getListarLigasAtivasUsuarioId(idUsuarioLogado).subscribe(
        response => {
          this.ligasAtivas = [];
          const lista = response.body;
          lista.forEach(element => {
            this.ligasAtivas.push(
              {
                idLiga: element.idLiga,
                nomeLiga: element.nomeLiga,
              }
            );
          });
        }, err => {
          this.dialogErroService.showMensagemErroDialog(err);
        }
      );
    }
  }

  mudaRankig(idLiga: string) {
    this.indicatorService.setIdLigaRankingSelecionado(idLiga);
  }

  toggleRightSidenav() {
    this.drawerMenu.toggle();
  }

  indicarLiga() {
    this.router.navigate(['tool/indicar-liga']);
  }

  alterarSenha() {
    this.router.navigate(['tool/alterar-senha']);
  }

  alterarAvatar() {
    this.router.navigate(['tool/alterar-avatar']);
  }

  atualizarCadastro() {
    this.router.navigate(['tool/atualizar-cadastro']);
  }

  verificaItensMenuGrupo() {
    const idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    const grupo = UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    if (grupo) {
      const grupoSelecionado = JSON.parse(grupo);
      if (Number(idUsuarioLogado) !== Number(grupoSelecionado.idUsuario) && grupoSelecionado.fechado) {
        this.indicatorService.setExibeIconeGrupoDetalhe(false);
      }
      if (Number(idUsuarioLogado) === Number(grupoSelecionado.idUsuario) && grupoSelecionado.fechado) {
        this.itensMenuGrupo.adicionarAmigos = true;
        this.itensMenuGrupo.convidarAmigos = true;
        this.itensMenuGrupo.editarGrupo = true;
        this.itensMenuGrupo.gerenciarGrupo = true;
      } else {
        if (!grupoSelecionado.fechado) {
          this.itensMenuGrupo.adicionarAmigos = true;
          this.itensMenuGrupo.convidarAmigos = true;
          this.itensMenuGrupo.editarGrupo = false;
          this.itensMenuGrupo.gerenciarGrupo = false;
          if (Number(idUsuarioLogado) === Number(grupoSelecionado.idUsuario)) {
            this.itensMenuGrupo.editarGrupo = true;
          }
        }
      }
    }
  }

  adicionarAmigos() {
    this.router.navigate(['tool/adicionar-amigos']);
  }

  convidarAmigos() {
    this.router.navigate(['tool/convidar-amigos']);
  }

  editarGrupo() {
    this.router.navigate(['tool/editar-grupo']);
  }

  gerenciarGrupo() {
    this.router.navigate(['tool/gerenciar-grupo']);
  }

  ouvinteExibicaoIconeMaisGrupos() {
    this.indicatorService.exibeIconeMaisGrupos.subscribe(
      (quotes) => {
        this.exibeIconeMaisGrupos = quotes;
      }
    );
  }

  criarGrupo() {
    this.router.navigate(['tool/criar-grupo']);
  }

  ouvinteExibicaoIconeDetalhesPalpites() {
    this.indicatorService.exibeIconeDetalhesPalpites.subscribe(
      (quotes) => {
        this.exibeIconeDetalhesPalpites = quotes;
      }
    );
  }

  detalhesPalpites() {
    this.router.navigate(['tool/detalhe-palpite']);
  }
}
