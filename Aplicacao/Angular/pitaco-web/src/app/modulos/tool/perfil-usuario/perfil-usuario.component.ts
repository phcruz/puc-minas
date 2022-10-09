import { Component, OnInit, OnDestroy } from '@angular/core';
import { Ranking } from 'src/app/models/ranking.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import * as $ from 'jquery';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit, OnDestroy {

  valueRanking: Ranking;
  gruposUsuario = [];
  partidasUsuario = [];
  idUsuarioLogado = '0';
  urlPlanoFundo = '';

  constructor(public activatedRoute: ActivatedRoute,
              private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private dialogErroService: DialogMensagemErroService,
              private indicatorService: IndicatorBehaviorService) { }

  async ngOnInit(): Promise<void> {
    this.indicatorService.setExibeIconePerfilUsuario(true);
    this.getPlanoFundo();

    const userRanking = UtilMetodos.getItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO);
    if (userRanking) {
      this.valueRanking = JSON.parse(userRanking);

      this.getGruposUsuario(this.valueRanking.idUsuario, Constants.INICIO_BUSCA);
      this.getPartidasPalpiteUsuario(this.valueRanking.idUsuario, Constants.INICIO_BUSCA, true);
    }
    this.ouvinteVerificaRolagemPagina();
  }

  getPlanoFundo() {
    this.appService.getPlanoFundo().subscribe(
      response => {
        this.urlPlanoFundo = response.body.urlImagem;
        $('#div-usuario-detalhe').css('background-image', 'url(' + this.urlPlanoFundo + ')');
        $('#div-usuario-detalhe').css('background-size', 'cover');

      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  getGruposUsuario(idUsuarioLogado, inicio) {
    this.appService.getListarGruposUsuarioIdPaginado(idUsuarioLogado, inicio).subscribe(
      response => {
        const lista = response.body;

        lista.forEach(element => {
          this.gruposUsuario.push(
            {
              idGrupo: element.idGrupo,
              idLiga: element.idLiga,
              idUsuario: element.idUsuario,
              nomeUsuarioAdmin: element.nomeUsuarioAdmin,
              descricaoGrupo: element.descricaoGrupo,
              descricaoPremioGrupo: element.descricaoPremioGrupo,
              mediaPontos: element.mediaPontos,
              quantidadeMembros: element.quantidadeMembros,
              fechado: element.fechado,
              pago: element.pago,
              valor: element.valor,

              tipoGrupo: element.fechado === true ? 'fechado.png' : 'publico.png',
              urlImagem: element.urlImagem,
              nomeGrupo: element.nomeGrupo,
              nomeLiga: element.nomeLiga,
              temporada: element.temporada,
              tipoAdmin: element.idUsuario == this.idUsuarioLogado ? 'admin_grupo.png' : 'ic_blank.png',
            }
          );
        });

    },
    err => {
      this.dialogErroService.showMensagemErroDialog(err);
    });
  }

  getPartidasPalpiteUsuario(idUsuario, inicio, limpaLista) {
    this.appService.getListarPalpitePartidaUsuarioPerfilPaginado(idUsuario, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.partidasUsuario = [];
        }

        if (lista.length < 30 && this.partidasUsuario.length > 0) {
          this.toastService.showToast('Lista partidas completa');
        }

        lista.forEach(element => {
          this.partidasUsuario.push(
            {
              idPartida: element.idPartida,
              idLiga: element.idLiga,
              idMandante: element.mandante.idEquipe,
              idVisitante: element.visitante.idEquipe,
              imgLogoLiga: element.urlImagemLogoLiga,
              nomeMandante: element.mandante.nome,
              imgLogoMandante: element.mandante.urlImagemEquipe,
              nomeVisitante: element.visitante.nome,
              imgLogoVisitante: element.visitante.urlImagemEquipe,
              localJogo: element.localJogo,
              permiteAposta: element.permiteAposta,
              mostraPontuacao: element.mostraPontuacao,
              dataHoraJogoTexto: element.dataHoraJogoTexto,
              placarEquipeCasa: element.placarEquipeCasa,
              placarEquipeVisitante: element.placarEquipeVisitante,
              tempoPartida: (element.tempoPartida !== null ? element.tempoPartida : ''),
              golsEquipeCasa: element.golsEquipeCasa,
              golsEquipeVisitante: element.golsEquipeVisitante,


              idPalpite: element.palpite.idPalpite,
              pontos: element.palpite.pontos <= 1 ? element.palpite.pontos + ' ponto' : element.palpite.pontos + ' pontos',
              localJogoDataHora: element.localJogo + ' ' + element.dataHoraJogoTexto,
              placarPartida: element.placarEquipeCasa + 'x' + element.placarEquipeVisitante,
              placarPalpite: element.palpite.placarEquipeCasa + 'x' + element.palpite.placarEquipeVisitante,
            }
          );
        });

      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      });
  }

  ngOnDestroy(): void {
    this.indicatorService.setExibeIconePerfilUsuario(false);
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/perfil-usuario') {
            this.getPartidasPalpiteUsuario(this.valueRanking.idUsuario, this.partidasUsuario.length, false);
          }
        }
      }
    );
  }

  abreTelaGrupoDetalhe(value: string) {
    UtilMetodos.setItemSessionStorage(Constants.GRUPO_SELECIONADO, JSON.stringify(value));
    this.router.navigate(['tool/detalhe-grupo']);
  }

  abreTelaPalpitePartida(value: string) {
    UtilMetodos.setItemSessionStorage(Constants.PARTIDA_SELECIONADA, JSON.stringify(value));
    this.router.navigate(['tool/palpite-partida']);
  }

  public getItemViewType(partida): any {
    if (partida.localJogo === Constants.JOGO_ADIADO && partida.idPalpite === 0) {
      partida.permiteAposta = true;
      partida.mostraPontuacao = false;
      return Constants.FACA_SUA_APOSTA;
    }
    if (partida.localJogo === Constants.JOGO_ADIADO && partida.idPalpite > 0) {
        partida.permiteAposta = true;
        partida.mostraPontuacao = false;
        return Constants.PALPITE_ANTES_JOGO;
    }
    if (partida.permiteAposta && partida.idPalpite === 0) {
        return Constants.FACA_SUA_APOSTA;
    }
    if (partida.permiteAposta && partida.idPalpite > 0) {
        return Constants.PALPITE_ANTES_JOGO;
    }
    if (!partida.permiteAposta && partida.idPalpite === 0) {
        return Constants.SEM_PALPITE_JOGO;
    }
    if (!partida.mostraPontuacao && partida.idPalpite > 0) {
        return Constants.PALPITE_ANTES_JOGO;
    }
    return Constants.COMPLETO;
  }
}
