import { Component, OnInit } from '@angular/core';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { AppService } from 'src/app/util/services/app.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-historico-partida',
  templateUrl: './historico-partida.component.html',
  styleUrls: ['./historico-partida.component.css']
})
export class HistoricoPartidaComponent implements OnInit {

  partidasHistorico = [];
  idLiga = '0';
  idEquipe = '0';

  constructor(private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.idLiga = UtilMetodos.getItemSessionStorage(Constants.HISTORICO_PARTIDA_ID_LIGA);
    this.idEquipe = UtilMetodos.getItemSessionStorage(Constants.HISTORICO_PARTIDA_ID_EQUIPE);

    this.listaHistoricoPartida(this.idLiga, this.idEquipe, Constants.INICIO_BUSCA, true);
    this.ouvinteVerificaRolagemPagina();
  }

  listaHistoricoPartida(idLiga, idEquipe, inicio, limpaLista) {
    this.appService.getListarHistoricoPartidasEquipe(idLiga, idEquipe, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.partidasHistorico = [];
        }

        if (lista.length < 30 && this.partidasHistorico.length > 0) {
          this.toastService.showToast('Histórico de partidas completo');
        }

        lista.forEach(element => {
          this.partidasHistorico.push(
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

              status: this.verificaStatusPartida(element.placarEquipeCasa,
                                                element.placarEquipeVisitante,
                                                element.mandante.idEquipe,
                                                element.visitante.idEquipe),

              localJogoDataHora: element.localJogo + ' ' + element.dataHoraJogoTexto
                              + (element.tempoPartida !== null ? ' | ' + element.tempoPartida : ''),
              placarPartida: element.placarEquipeCasa + 'x' + element.placarEquipeVisitante,
            }
          );
        });

      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  verificaStatusPartida(placarEquipeCasa, placarEquipeVisitante, idEquipeCasa, idEquipeVisitante): any {
    if (Number(placarEquipeCasa) === Number(placarEquipeVisitante)) {
      return 0;
    }

    if (Number(this.idEquipe) === Number(idEquipeCasa)) {
      if (Number(placarEquipeCasa) > Number(placarEquipeVisitante)) {
        return 1;
      } else {
        return 2;
      }
    }

    if (Number(this.idEquipe) === Number(idEquipeVisitante)) {
      if (Number(placarEquipeCasa) < Number(placarEquipeVisitante)) {
        return 1;
      } else {
        return 2;
      }
    }
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/historico-partida') {
            this.listaHistoricoPartida(this.idLiga, this.idEquipe, this.partidasHistorico.length, false);
          }
        }
      }
    );
  }
}
