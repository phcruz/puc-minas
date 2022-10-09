import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-pontuadores',
  templateUrl: './pontuadores.component.html',
  styleUrls: ['./pontuadores.component.css']
})
export class PontuadoresComponent implements OnInit {

  listaPontuadores = [];

  constructor(private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.getPontuadoresRodada(Constants.INICIO_BUSCA, true);
    this.ouvinteVerificaRolagemPagina();
  }

  getPontuadoresRodada(inicio, limpaLista) {
    this.appService.getListarPontuadorRodada(inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.listaPontuadores = [];
        }

        if (lista.length < 30 && this.listaPontuadores.length > 0) {
          this.toastService.showToast('Lista de pontuadores completa');
        }

        let indice = 1;
        lista.forEach(element => {
          this.listaPontuadores.push(
            {
              medalha: this.getTipoMedalha(indice++),
              idUsuario: element.idUsuario,
              nome: element.nome,
              urlImagem: element.urlImagem,
              pontos: element.pontos,
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  getTipoMedalha(indice: number): string {
    switch (indice) {
      case 1:
        return 'ic_medalha_ouro.png';
      case 2:
        return 'ic_medalha_prata.png';
      case 3:
        return 'ic_medalha_bronze.png';
      default:
        return '';
    }
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/pontuadores') {
            this.getPontuadoresRodada(this.listaPontuadores.length, false);
          }
        }
      }
    );
  }
}
