import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Constants } from 'src/app/util/constants';
import { ToastService } from 'src/app/util/services/toast.service';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { DialogConfirmacaoService } from 'src/app/util/services/dialog-confirmacao.service';

@Component({
  selector: 'app-adicionar-amigos',
  templateUrl: './adicionar-amigos.component.html',
  styleUrls: ['./adicionar-amigos.component.css']
})
export class AdicionarAmigosComponent implements OnInit {

  adicionarAmigosForm: FormGroup;
  idGrupo = '0';
  idLiga = '0';
  nomeGrupo = '';
  listaUsuario = [];

  constructor(private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private dialogConfirmacao: DialogConfirmacaoService) { }

  ngOnInit() {
    this.adicionarAmigosForm = new FormGroup({
      nomeAmigo: new FormControl('', [Validators.required, Validators.maxLength(30)]),
    });

    const grupo = UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    if (grupo) {
      const grupoSelecionado = JSON.parse(grupo);
      this.idGrupo = grupoSelecionado.idGrupo;
      this.idLiga = grupoSelecionado.idLiga;
      this.nomeGrupo = grupoSelecionado.nomeGrupo;
    }
  }

  submit() {
    if (this.adicionarAmigosForm.valid) {
      this.buscarListaUsuariosNome(Constants.INICIO_BUSCA);
    }
  }

  buscarListaUsuariosNome(inicio) {
    const nomeUsuario = this.adicionarAmigosForm.get('nomeAmigo').value;
    const idGrupo = this.idGrupo;

    this.appService.getListarUsuarioPorNome(nomeUsuario, inicio, idGrupo).subscribe(
      response => {
        this.listaUsuario = [];

        const lista = response.body;
        lista.forEach(element => {
          this.listaUsuario.push(
            {
              idUsuario: element.idUsuario,
              nome: element.nome,
              urlImagem: element.urlImagem
            }
          );
        });

        if (lista.length === 0) {
          this.toastService.showToast('Nenhum usuário encontrado 😁');
        }
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  abreModalConfirmacao(idUsuario, nome) {
    const texto = 'Adicionar ' + nome + ' ao grupo ' + this.nomeGrupo + '?';
    this.dialogConfirmacao.openDialog(texto, '');
    this.dialogConfirmacao.confirmed().subscribe(
      confirmed => {
        if (confirmed != null && confirmed !== undefined) {
          if (confirmed.data) {
            this.cadastrarGrupoUsuarioWebService(idUsuario);
          }
        }
      });
  }

  cadastrarGrupoUsuarioWebService(idUsuarioSelecionado) {
    const grupoUsuario = {
      idGrupo: this.idGrupo,
      idLiga: this.idLiga,
      idUsuario: idUsuarioSelecionado
    };

    this.appService.postParticiparGrupoUsuario(grupoUsuario).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.buscarListaUsuariosNome(Constants.INICIO_BUSCA);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
