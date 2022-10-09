import { Component, OnInit, OnDestroy } from '@angular/core';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { Ranking } from 'src/app/models/ranking.model';
import { Router } from '@angular/router';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.css']
})
export class RankingComponent implements OnInit, OnDestroy {

  listaRanking = [];
  idLiga: any;

  constructor(private appService: AppService,
              private router: Router,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.verificaInicializacaoRanking();
    this.ouvinteMudancaRanking();
    this.verificaPartidasOntem();
    this.ouvinteVerificaRolagemPagina();
  }

  verificaInicializacaoRanking() {
    this.indicatorService.setExibeIconeInfoLigas(true);
    this.idLiga = UtilMetodos.getItemSessionStorage(Constants.ID_LIGA_RANKING_SELECIONADO);
    if (this.idLiga !== undefined && this.idLiga !== null) {
      this.getRanking(this.idLiga, Constants.INICIO_BUSCA, true);
    }
  }

  getRanking(idLiga, inicio, limpaLista) {
    this.appService.getListarRankingPorLigaPaginado(idLiga, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.listaRanking = [];
        }

        if (lista.length < 30 && this.listaRanking.length > 0) {
          this.toastService.showToast('Ranking completo');
        }

        lista.forEach(element => {
          this.listaRanking.push(
            {
              posicao: element.posicao,
              pontos: element.pontos,
              nomeUsuario: element.nomeUsuario,
              urlImagem: element.urlImagem,
              acertoCincoPontos: element.acertoCincoPontos,
              acertoTresPontos: element.acertoTresPontos,
              acertoUmPonto: element.acertoUmPonto,
              acertoPontuou: element.acertoPontuou,
              idUsuario: element.idUsuario,
              idLiga: element.idLiga
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      });
  }

  verificaPartidasOntem() {
    this.appService.getTotalPartidasDiaAnterior().subscribe(
      response => {
        if (response.body > 0) {
          this.indicatorService.setExibeIconePontuadores(true);
        }
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  abreTela(value: Ranking) {
    UtilMetodos.setItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO, JSON.stringify(value));
    this.router.navigate(['tool/detalhe-usuario']);
  }

  ngOnDestroy(): void {
    this.indicatorService.setExibeIconeInfoLigas(false);
    this.indicatorService.setExibeIconePontuadores(false);
  }

  ouvinteMudancaRanking() {
    this.indicatorService.idLigaRankingSelecionado.subscribe(
      (quotes) => {
        if (quotes > 0) {
          this.idLiga = quotes;
          this.listaRanking = [];
          UtilMetodos.setItemSessionStorage(Constants.ID_LIGA_RANKING_SELECIONADO, quotes);
          this.getRanking(this.idLiga, Constants.INICIO_BUSCA, true);
        }
      }
    );
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/app/ranking') {
            this.getRanking(this.idLiga, this.listaRanking.length, false);
          }
        }
      }
    );
  }
}
