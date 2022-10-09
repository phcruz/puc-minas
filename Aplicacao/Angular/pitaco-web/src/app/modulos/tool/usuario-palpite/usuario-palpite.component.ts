import { Component, OnInit } from '@angular/core';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { AppService } from 'src/app/util/services/app.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-usuario-palpite',
  templateUrl: './usuario-palpite.component.html',
  styleUrls: ['./usuario-palpite.component.css']
})
export class UsuarioPalpiteComponent implements OnInit {

  listaUsuarioPalpite = [];
  estatisticaPartida: any;
  idPartida = '0';

  constructor(private appService: AppService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    const partida = UtilMetodos.getItemSessionStorage(Constants.PARTIDA_SELECIONADA);
    if (partida) {
      const partidaSelecionada = JSON.parse(partida);
      this.idPartida = partidaSelecionada.idPartida;
    }

    const estatistica = UtilMetodos.getItemSessionStorage(Constants.ESTATISTICA_PALPITE);
    if (estatistica) {
      this.estatisticaPartida = JSON.parse(estatistica);
      this.listaUsuariosPalpitePartida(this.idPartida, this.estatisticaPartida.placarPalpite, Constants.INICIO_BUSCA, true);
    }
    this.ouvinteVerificaRolagemPagina();
  }

  listaUsuariosPalpitePartida(idPartida, placarPalpite, inicio, limpaLista) {
    this.appService.getListarRankingUsuarioPorPalpite(idPartida, placarPalpite, inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.listaUsuarioPalpite = [];
        }

        if (lista.length < 30 && this.listaUsuarioPalpite.length > 0) {
          this.toastService.showToast('Lista de usuários por palpite completa');
        }

        lista.forEach(element => {
          this.listaUsuarioPalpite.push(element);
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/usuario-palpite') {
            this.listaUsuariosPalpitePartida(this.idPartida, this.estatisticaPartida.placarPalpite, this.listaUsuarioPalpite.length, false);
          }
        }
      }
    );
  }
}
