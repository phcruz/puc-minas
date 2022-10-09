import { Component, OnInit, OnDestroy } from '@angular/core';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { stringify } from 'querystring';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { Ranking } from 'src/app/models/ranking.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-detalhe-grupo',
  templateUrl: './detalhe-grupo.component.html',
  styleUrls: ['./detalhe-grupo.component.css']
})
export class DetalheGrupoComponent implements OnInit, OnDestroy {

  grupoSelecionado: any;
  listaRanking = [];

  constructor(private router: Router,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService) { }

  ngOnInit() {
    const grupo = UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    if (grupo) {
      this.grupoSelecionado = JSON.parse(grupo);
      this.grupoSelecionado.mediaPontos = (Math.round(this.grupoSelecionado.mediaPontos * 100) / 100).toFixed(1);

      this.getRanking(this.grupoSelecionado.idLiga, this.grupoSelecionado.idGrupo, Constants.INICIO_BUSCA, true);
      this.indicatorService.setExibeIconeGrupoDetalhe(true);
    }
    this.ouvinteVerificaRolagemPagina();
  }

  getRanking(idLiga, idGrupo, inicio, limpaLista) {
    this.appService.getListarRankingPorGrupoLigaPaginado(idLiga, idGrupo, inicio).subscribe(
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

  ngOnDestroy(): void {
    UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    this.indicatorService.setExibeIconeGrupoDetalhe(false);
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/detalhe-grupo') {
            this.getRanking(this.grupoSelecionado.idLiga, this.grupoSelecionado.idGrupo, this.listaRanking.length, false);
          }
        }
      }
    );
  }

  abreTelaDetalheUsuario(value: Ranking) {
    UtilMetodos.setItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO, JSON.stringify(value));
    this.router.navigate(['tool/detalhe-usuario']);
  }
}
