import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constants } from 'src/app/util/constants';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';
import { AppService } from 'src/app/util/services/app.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-ligas',
  templateUrl: './ligas.component.html',
  styleUrls: ['./ligas.component.css'],
  animations: [
    trigger('fadeIn', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(2000, style({opacity: 1}))
      ])
    ])
  ]
})
export class LigasComponent implements OnInit {

  ligasUsuario = [];
  idUsuarioLogado = '0';
  isSingleClick = true;

  constructor(private appService: AppService,
              private router: Router,
              private indicatorService: IndicatorBehaviorService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idUsuarioLogado !== '0') {
      this.getLigasUsuario(this.idUsuarioLogado, Constants.INICIO_BUSCA);
    }
  }

  getLigasUsuario(idUsuario, inicio) {
    this.appService.getListarLigasUsuarioIdPaginado(idUsuario, inicio).subscribe(
      response => {
        this.ligasUsuario = [];
        this.ligasUsuario = response.body;
      },
      err => {
        this.dialogErroService.showMensagemErroDialog(err);
      });
  }

  abreTela(value: string) {
    this.isSingleClick = true;
    setTimeout(() => {
      if (this.isSingleClick) {
        UtilMetodos.setItemSessionStorage(Constants.ID_LIGA, value);
        this.indicatorService.setExibeIconeClassificacaoGeral(true);
        this.router.navigate(['tool/partidas-liga']);
      }
    }, 250);
  }

  onDoubleClick(value: any) {
    this.isSingleClick = false;
    this.openDialogConfirmacaoImportarLiga(value);
  }

  openDialogConfirmacaoImportarLiga(value) {
    const information = 'Deseja realmente sair da liga ' + value.nomeLiga + '?';
    this.dialogConfirmacao.openDialog(information, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            const ligaUsuario = this.preencheObjetoLigaUsuario(value.idLiga);
            this.postImportarLigaUsuario(ligaUsuario);
          }
        }
      }
    );
  }

  preencheObjetoLigaUsuario(idLigaImport): any {
    const ligaUsuario = {
      idLiga: idLigaImport,
      idUsuario: this.idUsuarioLogado
    }

    return ligaUsuario;
  }

  postImportarLigaUsuario(ligaUsuario) {
    this.appService.postImportarLigaUsuario(ligaUsuario).subscribe(
      response => {
        const mensagem = response.body;
        this.toastService.showToast(mensagem);
        this.getLigasUsuario(this.idUsuarioLogado, Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
