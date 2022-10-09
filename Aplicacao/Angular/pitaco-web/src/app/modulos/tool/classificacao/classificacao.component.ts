import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/util/constants';
import { AppService } from 'src/app/util/services/app.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-classificacao',
  templateUrl: './classificacao.component.html',
  styleUrls: ['./classificacao.component.css']
})
export class ClassificacaoComponent implements OnInit {

  equipesClassificacao = [];
  idLiga: any;
  idUsuarioLogado = '0';

  constructor(private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    this.idLiga = UtilMetodos.getItemSessionStorage(Constants.ID_LIGA);
    if (this.idLiga !== null && this.idLiga !== undefined && this.idLiga > 0) {
      this.getEquipesClassificacao(this.idLiga, Constants.INICIO_BUSCA, true);
    }
    this.ouvinteVerificaRolagemPagina();
  }

  getEquipesClassificacao(idLiga, inicio, limpaLista) {
    this.appService.getListaClassificacaoGeralLiga(idLiga, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.equipesClassificacao = [];
        }

        if (lista.length < 30 && this.equipesClassificacao.length > 0) {
          this.toastService.showToast('Classificação completa');
        }

        lista.forEach(element => {
          this.equipesClassificacao.push(
            {
              posicao: element.posicao,
              urlImagemEquipe: element.urlImagemEquipe,
              nomeEquipe: element.nomeEquipe,
              quantidadePontos: element.quantidadePontos,
              quantidadeJogos: element.quantidadeJogos,
              quantidadeVitorias: element.quantidadeVitorias,
              quantidadeEmpates: element.quantidadeEmpates,
              quantidadeDerrotas: element.quantidadeDerrotas,
            }
          );
        });
      },
      err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/classificacao') {
            this.getEquipesClassificacao(this.idLiga, this.equipesClassificacao.length, false);
          }
        }
      }
    );
  }
}
