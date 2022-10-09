import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';
import { ToastService } from 'src/app/util/services/toast.service';

@Component({
  selector: 'app-mais-ligas',
  templateUrl: './mais-ligas.component.html',
  styleUrls: ['./mais-ligas.component.css']
})
export class MaisLigasComponent implements OnInit {

  ligasImportar = [];
  idUsuarioLogado = '0';

  constructor(private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idUsuarioLogado !== '0') {
      this.getListarLigasParaImportar(this.idUsuarioLogado, Constants.INICIO_BUSCA);
    }
  }

  getListarLigasParaImportar(idUsuarioLogado, inicio) {
    this.appService.getListarLigasParaImportarPaginado(idUsuarioLogado, inicio).subscribe(
      response => {
        this.ligasImportar = [];
        this.ligasImportar = response.body;
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  openDialogConfirmacaoImportarLiga(value) {
    const information = 'Tem certeza que deseja importar ' + value.nomeLiga + '?';
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
        this.getListarLigasParaImportar(this.idUsuarioLogado, Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
