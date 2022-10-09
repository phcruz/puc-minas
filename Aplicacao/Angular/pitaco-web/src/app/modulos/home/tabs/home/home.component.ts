import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/util/services/toast.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { WebSocketService } from 'src/app/util/services/websocket.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  partidasHoje = [];
  idUsuarioLogado = '0';
  showSpinner = false;
  isPartidaAndamento = false;
  interval;

  constructor(private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService,
              private webSocketService: WebSocketService) { }

  ngOnInit() {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idUsuarioLogado !== '0') {
      this.getPartidasHoje(this.idUsuarioLogado, Constants.INICIO_BUSCA, true);
    }
    this.ouvinteVerificaRolagemPagina();
    // this.connectWebsocket();
  }

  getPartidasHoje(idUsuario, inicio, limpaLista) {
    this.appService.getListarPartidasHojeLigasUsuarioPaginado(idUsuario, inicio).subscribe(
      response => {
        const lista = response.body;
        // this.preenchePartidas(lista, limpaLista);
        if (limpaLista) {
          this.partidasHoje = [];
        }

        if (lista.length < 30 && this.partidasHoje.length > 0) {
          this.toastService.showToast('Partidas de hoje completa');
        }

        lista.forEach(element => {
          this.partidasHoje.push(
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
              tempoPartida: (element.tempoPartida !== null ? element.tempoPartida : null),
              golsEquipeCasa: element.golsEquipeCasa,
              golsEquipeVisitante: element.golsEquipeVisitante,
              placarExtendidoEquipeCasa: element.placarExtendidoEquipeCasa,
              placarExtendidoEquipeVisitante: element.placarExtendidoEquipeVisitante,

              idPalpite: element.palpite.idPalpite,
              pontos: element.palpite.pontos <= 1 ? element.palpite.pontos + ' ponto' : element.palpite.pontos + ' pontos',
              localJogoDataHora: element.localJogo + ' ' + element.dataHoraJogoTexto,
              placarPartida: element.placarEquipeCasa + 'x' + element.placarEquipeVisitante,
              placarPalpite: element.palpite.placarEquipeCasa + 'x' + element.palpite.placarEquipeVisitante,
            }
          );
        });

        this.showSpinner = false;
        this.verificaPartidasAndamento();
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      });
  }

  public preenchePartidas(lista, limpaLista) {
    if (limpaLista) {
      this.partidasHoje = [];
    }

    if (lista.length < 30 && this.partidasHoje.length > 0) {
      this.toastService.showToast('Partidas de hoje completa');
    }

    lista.forEach(element => {
      this.partidasHoje.push(
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
          tempoPartida: (element.tempoPartida !== null ? element.tempoPartida : null),
          golsEquipeCasa: element.golsEquipeCasa,
          golsEquipeVisitante: element.golsEquipeVisitante,
          placarExtendidoEquipeCasa: element.placarExtendidoEquipeCasa,
          placarExtendidoEquipeVisitante: element.placarExtendidoEquipeVisitante,

          idPalpite: element.palpite.idPalpite,
          pontos: element.palpite.pontos <= 1 ? element.palpite.pontos + ' ponto' : element.palpite.pontos + ' pontos',
          localJogoDataHora: element.localJogo + ' ' + element.dataHoraJogoTexto,
          placarPartida: element.placarEquipeCasa + 'x' + element.placarEquipeVisitante,
          placarPalpite: element.palpite.placarEquipeCasa + 'x' + element.palpite.placarEquipeVisitante,
        }
      );
    });

    this.showSpinner = false;
    this.verificaPartidasAndamento();
  }

  abreTelaPalpitePartida(value: string) {
    UtilMetodos.setItemSessionStorage(Constants.PARTIDA_SELECIONADA, JSON.stringify(value));
    this.router.navigate(['tool/palpite-partida']);
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/app/home') {
            this.getPartidasHoje(this.idUsuarioLogado, Constants.INICIO_BUSCA, true);
          }
        }
      }
    );
  }

  startTimer(seconds) {
    let counter = seconds;
    this.interval = setInterval(() => {
      counter--;
      if (counter === 1) {
        this.showSpinner = true;
      }
      if (counter < 0 ) {
        counter = 30;
        clearInterval(this.interval);
        this.getPartidasHoje(this.idUsuarioLogado, Constants.INICIO_BUSCA, true);
        // this.sendMessageWebsocket(this.idUsuarioLogado, Constants.INICIO_BUSCA);
        return;
      }
    }, Constants.TEMPO_ESPERA_BUSCA_PARTIDAS);
  }

  verificaPartidasAndamento() {
    this.isPartidaAndamento = false;
    let contador = 0;
    this.partidasHoje.forEach(element => {
      if (element.tempoPartida !== null && !Object.is(element.tempoPartida, 'Encerrado')
              && this.verificaDataHoraPartida(element.dataHoraJogoTexto)) {
        contador++;
      }
    });
    if (contador > 0) {
      this.isPartidaAndamento = true;
      clearInterval(this.interval);
      this.startTimer(30);
    }
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
    // this.disconnectWebsocket();
  }

  verificaDataHoraPartida(dataPartidaTexto: string): boolean {
    const dataPartida = UtilMetodos.convertDateTimeStringDate(dataPartidaTexto);
    dataPartida.setMinutes(dataPartida.getMinutes() + Constants.QUANTIDADE_MINUTOS_PARTIDA);
    const data = new Date(Date.now());

    const realizaBuscaPlacar = UtilMetodos.comparaData(data, dataPartida);

    return realizaBuscaPlacar;
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

  /*
  public connectWebsocket() {
    this.webSocketService.connect().subscribe(
      msg => {
        if (!JSON.stringify(msg).includes('Websocket conectado')) {
          this.preenchePartidas(msg, true);
        }
      },
      err => {
        console.log(err);
      },
      () => console.log('complete')
   );
  }

  public sendMessageWebsocket(idUsuario, inicio) {
    const message = {
      idUsuario: idUsuario,
      inicio: inicio
     };
     this.webSocketService.sendMessage(message);
  }

  public disconnectWebsocket() {
    this.webSocketService.closeConnection();
  }
  */
}
