import { Component, OnInit } from '@angular/core';
import { Constants } from 'src/app/util/constants';
import { ToastService } from 'src/app/util/services/toast.service';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';
import { UtilMetodos } from 'src/app/util/util-metodos';

@Component({
  selector: 'app-gerenciar-grupo',
  templateUrl: './gerenciar-grupo.component.html',
  styleUrls: ['./gerenciar-grupo.component.css']
})
export class GerenciarGrupoComponent implements OnInit {

  listaConvites = [];
  idGrupo = '0';
  idLiga = '0';
  nomeGrupo = '';

  constructor(private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService) { }

  ngOnInit() {
    const grupo = UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    if (grupo) {
      const grupoSelecionado = JSON.parse(grupo);
      this.idGrupo = grupoSelecionado.idGrupo;
      this.idLiga = grupoSelecionado.idLiga;
      this.nomeGrupo = grupoSelecionado.nomeGrupo;

      this.buscarSolicitacoesPendentes(Constants.INICIO_BUSCA);
    }
  }

  buscarSolicitacoesPendentes(inicio) {
    this.appService.getListarSolicitacaoGrupo(this.idGrupo, inicio).subscribe(
      response => {
        this.listaConvites = [];

        const lista = response.body;
        lista.forEach(element => {
          this.listaConvites.push(
            {
              idSolicitacaoGrupoUsuario: element.idSolicitacaoGrupoUsuario,
              idGrupo: element.idGrupo,
              idLiga: element.idLiga,
              idUsuarioAdmin: element.idUsuarioAdmin,
              idUsuarioSolicitante: element.idUsuarioSolicitante,
              dataSolicitacao: element.dataSolicitacao,
              permiteParticipar: element.permiteParticipar,
              ativo: element.ativo,
              urlImagemAvatarUsuario: element.urlImagemAvatarUsuario,
              nomeUsuario: element.nomeUsuario
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  abreModalConfirmacao(solicitacao) {
    const texto = 'Permitir ' + solicitacao.nomeUsuario + ' participar do grupo?';
    this.dialogConfirmacao.openDialog(texto, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            solicitacao.permiteParticipar = true;
          } else {
            solicitacao.permiteParticipar = false;
          }
          this.alteraSolicitacaoGrupoUsuario(solicitacao);
        }
      });
  }

  alteraSolicitacaoGrupoUsuario(solicitacao) {
    this.appService.putAlteraSolicitacaoGrupoUsuario(solicitacao).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.buscarSolicitacoesPendentes(Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
