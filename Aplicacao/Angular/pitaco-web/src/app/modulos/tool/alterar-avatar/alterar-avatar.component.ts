import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { Constants } from 'src/app/util/constants';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { Ranking } from 'src/app/models/ranking.model';
import { Router } from '@angular/router';
import { IndicatorBehaviorService } from 'src/app/util/services/indicator-behavior.service';

@Component({
  selector: 'app-alterar-avatar',
  templateUrl: './alterar-avatar.component.html',
  styleUrls: ['./alterar-avatar.component.css']
})
export class AlterarAvatarComponent implements OnInit {

  valueRanking: Ranking;
  listaAvatar = [];

  constructor(private appService: AppService,
              private router: Router,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacaoService: DialogConfirmacaoService,
              private toastService: ToastService,
              private indicatorService: IndicatorBehaviorService) { }

  ngOnInit() {
    this.getListaAvatares(Constants.INICIO_BUSCA, true);
    this.ouvinteVerificaRolagemPagina();
  }

  getListaAvatares(inicio, limpaLista) {
    this.appService.getAvatares(inicio).subscribe(
      response => {
        const lista = response.body;

        if (limpaLista) {
          this.listaAvatar = [];
        }

        if (lista.length < 30 && this.listaAvatar.length > 0) {
          this.toastService.showToast('Lista de avatares completa');
        }

        lista.forEach(element => {
          this.listaAvatar.push(
            {
              idAvatar: element.idAvatar,
              descricao: element.descricao,
              urlImagem: element.urlImagem,
              dataCadastro: element.dataCadastro,
              ativo: element.ativo,
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  alterarAvatar(avatar: any) {
    this.dialogConfirmacaoService.openDialog('Deseja realmente alterar seu avatar?', '');
    this.dialogConfirmacaoService.confirmed().subscribe(
      res => {
        if (res) {
          this.realizaAlteracaoAvatar(avatar.idAvatar, avatar.urlImagem);
        }
      }
    );

  }

  realizaAlteracaoAvatar(idAvatar, urlImagem) {
    const idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);

    this.appService.putAtualizaAvatarUsuario(idAvatar, idUsuarioLogado).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.alteraRankingUsuarioSelecionado(urlImagem);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  alteraRankingUsuarioSelecionado(urlImagem) {
    const userRanking = UtilMetodos.getItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO);
    if (userRanking) {
      this.valueRanking = JSON.parse(userRanking);

      this.valueRanking.urlImagem = urlImagem;
      UtilMetodos.setItemSessionStorage(Constants.USUARIO_RANKING_SELECIONADO, JSON.stringify(this.valueRanking));
      UtilMetodos.setItemSessionStorage(Constants.AVATAR_USUARIO_LOGADO, urlImagem);

      this.indicatorService.setSalvaDadosUsuarioLogado(Constants.SALVA_AVATAR);
      this.router.navigate(['tool/perfil-usuario']);
    }
  }

  ouvinteVerificaRolagemPagina() {
    this.indicatorService.realizaChamadaScroll.subscribe(
      (quotes) => {
        if (quotes) {
          if (quotes === '/tool/alterar-avatar') {
            this.getListaAvatares(this.listaAvatar.length, false);
          }
        }
      }
    );
  }
}
