import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Router } from '@angular/router';
import { AppService } from 'src/app/util/services/app.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-detalhe-palpite',
  templateUrl: './detalhe-palpite.component.html',
  styleUrls: ['./detalhe-palpite.component.css']
})
export class DetalhePalpiteComponent implements OnInit {

  idPartida = '0';
  partidaSelecionada: any;
  listaDados = [];

  constructor(private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    const partida = UtilMetodos.getItemSessionStorage(Constants.PARTIDA_SELECIONADA);
    if (partida) {
      this.partidaSelecionada = JSON.parse(partida);
    }
    this.idPartida = UtilMetodos.getItemSessionStorage(Constants.DETALHE_PALPITE_ID_PARTIDA);
    if (this.idPartida !== null && this.idPartida !== undefined) {
      this.buscarEstatisticaPlacarPartida(this.idPartida, Constants.INICIO_BUSCA, true);
    }
    this.ouvinteVerificaRolagemPagina();
  }

  buscarEstatisticaPlacarPartida(idPartida, inicio, limpaLista) {
    this.appService.getListarEstatisticaPlacarPartidaId(idPartida, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.listaDados = [];
        }

        if (lista.length < 30 && this.listaDados.length > 0) {
          this.toastService.showToast('Detalhes palpite completo');
        }

        lista.forEach(element => {
          this.listaDados.push(element);
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  abreTelaUsuarioPorPalpite(value) {
    UtilMetodos.setItemSessionStorage(Constants.ESTATISTICA_PALPITE, JSON.stringify(value));
    this.router.navigate(['tool/usuario-palpite']);
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/detalhe-palpite') {
            this.buscarEstatisticaPlacarPartida(this.idPartida, this.listaDados.length, false);
          }
        }
      }
    );
  }
}
