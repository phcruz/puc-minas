import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-mais-grupos',
  templateUrl: './mais-grupos.component.html',
  styleUrls: ['./mais-grupos.component.css']
})
export class MaisGruposComponent implements OnInit, OnDestroy {

  gruposImportar = [];
  idUsuarioLogado = '0';

  constructor(private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService) { }

  async ngOnInit(): Promise<void> {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idUsuarioLogado !== '0') {
      this.getListaGruposParaImportar(this.idUsuarioLogado, Constants.INICIO_BUSCA);
      this.indicatorService.setExibeIconeMaisGrupos(true);
    }
  }

  getListaGruposParaImportar(idusuario, inicio) {
    this.appService.getListarGruposParaImportarPaginado(idusuario, inicio).subscribe(
      response => {
        this.gruposImportar = [];
        const lista = response.body;

        lista.forEach(element => {
          this.gruposImportar.push(
            {
              idGrupo: element.idGrupo,
              idLiga: element.idLiga,
              idUsuario: element.idUsuario,
              nomeUsuarioAdmin: element.nomeUsuarioAdmin,
              descricaoGrupo: element.descricaoGrupo,
              descricaoPremioGrupo: element.descricaoPremioGrupo,
              mediaPontos: element.mediaPontos,
              quantidadeMembros: element.quantidadeMembros,
              tipoGrupo: element.fechado === true ? 'fechado.png' : 'publico.png',
              urlImagem: element.urlImagem,
              nomeGrupo: element.nomeGrupo,
              nomeLiga: element.nomeLiga,
              temporada: element.temporada,
              fechado: element.fechado,
              pago: element.pago,
              valor: element.valor,
              tipoAdmin: element.idUsuario == this.idUsuarioLogado ? 'admin_grupo.png' : 'ic_blank.png',
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  openDialogConfirmacaoImportarGrupo(value) {
    const titulo = value.fechado ? 'Solicitação de participação' : 'Participar do grupo';
    let mensagem = value.fechado
                ? 'Tem certeza que deseja enviar uma solicitação de participação ao grupo '
                : 'Tem certeza que deseja participar do grupo ';
    mensagem = mensagem + value.nomeGrupo;

    this.dialogConfirmacao.openDialog(titulo, mensagem);
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            if (value.fechado) {
              // manda solicitação de participacao
              this.cadastrarSolicitacaoGrupoUsuarioWebService(value);
            } else {
              this.cadastrarGrupoUsuarioWebService(value.idGrupo, value.idLiga);
            }
          }
        }
      }
    );
  }

  cadastrarSolicitacaoGrupoUsuarioWebService(grupo) {
    const solicitacao = this.preencheObjetoSolicitacaoGrupoUsuario(grupo);
    this.appService.postCadastroSolicitacaoGrupoUsuario(solicitacao).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.getListaGruposParaImportar(this.idUsuarioLogado, Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  preencheObjetoSolicitacaoGrupoUsuario(grupo): any {
    const solicitacao = {
      idGrupo: grupo.idGrupo,
      idLiga: grupo.idLiga,
      idUsuarioAdmin: grupo.idUsuario,
      idUsuarioSolicitante: this.idUsuarioLogado
    }

    return solicitacao;
  }

  cadastrarGrupoUsuarioWebService(idGrupo, idLiga) {
    const grupoUsuario = this.preencheObjetoGrupoUsuario(idGrupo, idLiga);
    this.appService.postParticiparGrupoUsuario(grupoUsuario).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.getListaGruposParaImportar(this.idUsuarioLogado, Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  preencheObjetoGrupoUsuario(idGrupoSelect, idLigaSelect): any {
    const grupoUsuario = {
      idGrupo: idGrupoSelect,
      idLiga: idLigaSelect,
      idUsuario: this.idUsuarioLogado
    }

    return grupoUsuario;
  }

  ngOnDestroy(): void {
    this.indicatorService.setExibeIconeMaisGrupos(false);
  }
}
