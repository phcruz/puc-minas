import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';

@Component({
  selector: 'app-grupos',
  templateUrl: './grupos.component.html',
  styleUrls: ['./grupos.component.css'],
  animations: [
    trigger('fadeIn', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(2000, style({opacity: 1}))
      ])
    ])
  ]
})
export class GruposComponent implements OnInit {

  gruposUsuario = [];
  idUsuarioLogado = '0' ;
  isSingleClick = true;

  constructor(private appService: AppService,
              private router: Router,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService,
              private toastService: ToastService) { }

  async ngOnInit(): Promise<void> {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    if (this.idUsuarioLogado !== '0') {
      this.getGruposUsuario(this.idUsuarioLogado, Constants.INICIO_BUSCA);
    }
    // const promise = await this.appService.getDadosUsuario();
    // if (promise !== null && promise !== undefined) {
    //   let parts = [];
    //   parts = promise.transaction.split('@');
    //   this.idUsuarioLogado = parts[1];
    // }
  }

  getGruposUsuario(idUsuario, inicio) {
    this.appService.getListarGruposUsuarioIdPaginado(idUsuario, inicio).subscribe(
      response => {
        this.gruposUsuario = [];
        const lista = response.body;

        lista.forEach(element => {
          this.gruposUsuario.push(
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
    },
    err => {
      this.dialogErroService.showMensagemErroDialog(err);
    });
  }

  abreTela(value: string) {
    this.isSingleClick = true;
    setTimeout(() => {
      if (this.isSingleClick) {
        UtilMetodos.setItemSessionStorage(Constants.GRUPO_SELECIONADO, JSON.stringify(value));
        this.router.navigate(['tool/detalhe-grupo']);
      }
    }, 250);
  }

  onDoubleClick(value: any) {
    this.isSingleClick = false;
    this.openDialogConfirmacaoImportarGrupo(value);
  }

  openDialogConfirmacaoImportarGrupo(value) {
    const information = 'Deseja realmente sair do grupo ' + value.nomeGrupo + '?';
    this.dialogConfirmacao.openDialog(information, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            this.cadastrarGrupoUsuarioWebService(value.idGrupo, value.idLiga);
          }
        }
      }
    );
  }

  cadastrarGrupoUsuarioWebService(idGrupo, idLiga) {
    const grupoUsuario = this.preencheObjetoGrupoUsuario(idGrupo, idLiga);
    this.appService.postParticiparGrupoUsuario(grupoUsuario).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.getGruposUsuario(this.idUsuarioLogado, Constants.INICIO_BUSCA);
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
    };

    return grupoUsuario;
  }
}
