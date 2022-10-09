import { Component, OnInit, OnDestroy } from '@angular/core';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ToastService } from 'src/app/util/services/toast.service';
import { Router } from '@angular/router';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { PreviousRouteServiceService } from 'src/app/util/services/previous-route-service.service';
import * as $ from 'jquery';

@Component({
  selector: 'app-palpite-partida',
  templateUrl: './palpite-partida.component.html',
  styleUrls: ['./palpite-partida.component.css']
})
export class PalpitePartidaComponent implements OnInit, OnDestroy {

  partidaPalpiteForm: FormGroup;
  urlAnterior = '';
  idUsuarioLogado = '0';
  partidaSelecionada: any;
  estatisticaPartida: any;
  estatisticaPlacar: any[];
  exibeGrafico = false;
  listaPlacar = [];
  colorScheme = {
    domain: ['#FF6600', '#5AA454', '#A10A28', '#F5C700', '#AAAAAA']
  };

  colorSchemePizza = {
    domain: ['#0092CE', '#76FF02', '#90A4AD']
  };

  constructor(private router: Router,
              private appService: AppService,
              private toastService: ToastService,
              private dialogErroService: DialogMensagemErroService,
              private indicatorService: IndicatorBehaviorService,
              private previousRouteService: PreviousRouteServiceService) { }

  ngOnInit() {
    this.urlAnterior = this.previousRouteService.getPreviousUrl();
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    this.partidaPalpiteForm = new FormGroup({
      palpiteEquipeCasa: new FormControl('', [Validators.required, Validators.max(20)]),
      palpiteEquipeVisitante: new FormControl('', [Validators.required, Validators.max(20)]),
    });

    this.inicializaListaPlacar();

    const partida = UtilMetodos.getItemSessionStorage(Constants.PARTIDA_SELECIONADA);
    if (partida) {
      this.partidaSelecionada = JSON.parse(partida);
      if (this.partidaSelecionada.mostraPontuacao) {
        UtilMetodos.setItemSessionStorage(Constants.DETALHE_PALPITE_ID_PARTIDA, this.partidaSelecionada.idPartida);
      }

      if (this.partidaSelecionada.mostraPontuacao && this.partidaSelecionada.localJogo !== 'JOGO ADIADO') {
        this.indicatorService.setExibeIconeDetalhesPalpites(true);
        this.buscaEstatisticasPartida();
      }

      this.verificaPreencheDadosPalpite();
    }
  }

  submit() {
    if (this.partidaPalpiteForm.valid) {
      const palpite = this.preencheObjetoPalpite();
      this.enviaPalpitePartida(palpite);
    }
  }

  ngOnDestroy(): void {
    this.indicatorService.setExibeIconeDetalhesPalpites(false);
  }

  abreHistoricoPartida(idLiga, idEquipe) {
    UtilMetodos.setItemSessionStorage(Constants.HISTORICO_PARTIDA_ID_LIGA, idLiga);
    UtilMetodos.setItemSessionStorage(Constants.HISTORICO_PARTIDA_ID_EQUIPE, idEquipe);
    this.router.navigate(['tool/historico-partida']);
  }

  onSelect(event) {
    this.toastService.showToast('Total: ' + event.value);
  }

  buscaEstatisticasPartida() {
    const idPartida = this.partidaSelecionada.idPartida;

    this.appService.getBuscarEstatisticasPartida(idPartida).subscribe(
      response => {
        this.exibeGrafico = true;
        this.setPlanoFundo();

        this.estatisticaPartida = [
          {
            name: this.partidaSelecionada.nomeMandante,
            value: response.body.vitoriaCasa
          },
          {
            name: this.partidaSelecionada.nomeVisitante,
            value: response.body.vitoriaVisitante
          },
          {
            name: 'Empate',
            value: response.body.empate
          },
        ];

        const lista = response.body.listaEstatisticaPlacar;
        const formatList = [];
        lista.forEach(element => {
          formatList.push(
            {
              name: element.placarPalpite,
              value: element.totalPlacar
            });
        });

        this.estatisticaPlacar = formatList;
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  inicializaListaPlacar() {
    for (let i = 0; i <= 10; i++) {
      this.listaPlacar.push({ valor: i});
    }
  }

  verificaPreencheDadosPalpite() {
    if (Number(this.partidaSelecionada.idPalpite) > 0) {
      const gols = this.partidaSelecionada.placarPalpite.split('x');
      this.partidaPalpiteForm.controls['palpiteEquipeCasa'].setValue(Number(gols[0]));
      this.partidaPalpiteForm.controls['palpiteEquipeVisitante'].setValue(Number(gols[1]));
    }
  }

  preencheObjetoPalpite(): any {
    const palpite = {
      idPalpite: this.partidaSelecionada.idPalpite,
      idPartida: this.partidaSelecionada.idPartida,
      idUsuario: Number(this.idUsuarioLogado),
      idLiga: this.partidaSelecionada.idLiga,
      placarEquipeCasa: this.partidaPalpiteForm.get('palpiteEquipeCasa').value,
      placarEquipeVisitante: this.partidaPalpiteForm.get('palpiteEquipeVisitante').value,
    };

    return palpite;
  }

  enviaPalpitePartida(palpite) {
    this.appService.postCadastraPalpitePartida(palpite).subscribe(
      response => {
        const { idPalpite, placarEquipeCasa, placarEquipeVisitante } = response.body;
        this.partidaSelecionada.idPalpite = idPalpite;
        this.partidaSelecionada.placarPalpiteCasa = placarEquipeCasa;
        this.partidaSelecionada.placarPalpiteVisitante = placarEquipeVisitante;
        this.partidaSelecionada.placarPalpite = placarEquipeCasa + 'x' + placarEquipeVisitante;
        UtilMetodos.setItemSessionStorage(Constants.PARTIDA_SELECIONADA, JSON.stringify(this.partidaSelecionada));

        this.toastService.showToast('Palpite cadastrado com sucesso. Boa sorte 😁.');
        this.verificaUrlProximaNavegacao();
      }, err => {
        if (err.status === 417) {
          this.toastService.showToast(err.error.message);
          this.verificaUrlProximaNavegacao();
        } else {
          this.dialogErroService.showMensagemErroDialog(err);
        }
      }
    );
  }

  verificaUrlProximaNavegacao() {
    // /app/home ou /tool/palpite-partida
    if (this.urlAnterior === '/app/home' || this.urlAnterior === '/tool/palpite-partida') {
      this.router.navigate([this.urlAnterior]);
    }
  }

  setPlanoFundo() {
    const urlPlanoFundo = 'https://www.villagefc.co.uk/wp-content/uploads/2015/06/grass-bg-compressed.jpg';
    $('#card-container-palpite-partida').css('background-image', 'url(' + urlPlanoFundo + ')');
    $('#card-container-palpite-partida').css('background-size', 'cover');
  }
}
