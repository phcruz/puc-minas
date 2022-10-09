import { Component, OnInit, OnDestroy } from '@angular/core';
import { Constants } from 'src/app/util/constants';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { AppService } from 'src/app/util/services/app.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-partidas-liga',
  templateUrl: './partidas-liga.component.html',
  styleUrls: ['./partidas-liga.component.css']
})
export class PartidasLigaComponent implements OnInit, OnDestroy  {

  partidasLiga = [];
  idLiga: any;
  idUsuarioLogado = '0';

  constructor(private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) {
  }

  ngOnInit() {
    this.indicatorService.setExibeIconeClassificacaoGeral(true);
    this.idLiga = UtilMetodos.getItemSessionStorage(Constants.ID_LIGA);
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idLiga !== null && this.idLiga !== undefined) {
      if (this.idUsuarioLogado !== null && this.idUsuarioLogado !== undefined) {
        this.getPartidasLiga(this.idUsuarioLogado, this.idLiga, Constants.INICIO_BUSCA, true);
      }
    }
    this.ouvinteVerificaRolagemPagina();
  }

  getPartidasLiga(idUsuarioLogado, idLiga, inicio, limpaLista) {
    this.appService.getListarPartidasLigaUsuarioPaginado(idUsuarioLogado, idLiga, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.partidasLiga = [];
        }

        if (lista.length < 30 && this.partidasLiga.length > 0) {
          this.toastService.showToast('Lista de partidas completa');
        }

        lista.forEach(element => {
          this.partidasLiga.push(
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
              tempoPartida: element.tempoPartida,
              golsEquipeCasa: element.golsEquipeCasa,
              golsEquipeVisitante: element.golsEquipeVisitante,
              placarExtendidoEquipeCasa: element.placarExtendidoEquipeCasa,
              placarExtendidoEquipeVisitante: element.placarExtendidoEquipeVisitante,

              idPalpite: element.palpite.idPalpite,
              placarPalpiteCasa: element.palpite.placarEquipeCasa,
              placarPalpiteVisitante: element.palpite.placarEquipeVisitante,
              pontos: element.palpite.pontos <= 1 ? element.palpite.pontos + ' ponto' : element.palpite.pontos + ' pontos',
              localJogoDataHora: element.localJogo + ' ' + element.dataHoraJogoTexto
                              + (element.tempoPartida !== null ? ' | ' + element.tempoPartida : ''),
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
    this.indicatorService.setExibeIconeClassificacaoGeral(false);
  }

  abreTelaPalpitePartida(value: string) {
    UtilMetodos.setItemSessionStorage(Constants.PARTIDA_SELECIONADA, JSON.stringify(value));
    this.router.navigate(['tool/palpite-partida']);
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/partidas-liga') {
            this.getPartidasLiga(this.idUsuarioLogado, this.idLiga, this.partidasLiga.length, false);
          }
        }
      }
    );
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
