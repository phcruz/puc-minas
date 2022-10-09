import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/util/services/toast.service';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import * as $ from 'jquery';

@Component({
  selector: 'app-criar-grupo',
  templateUrl: './criar-grupo.component.html',
  styleUrls: ['./criar-grupo.component.css']
})
export class CriarGrupoComponent implements OnInit {

  criarGrupoForm: FormGroup;
  ligasAtivas = [];
  tipoGrupo = false;
  idUsuarioLogado = '0';

  constructor(private router: Router,
              private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.criarGrupoForm = new FormGroup({
      nomeGrupo: new FormControl('', [Validators.required, Validators.maxLength(30)]),
      descricaoGrupo: new FormControl('', [Validators.required, Validators.maxLength(90)]),
      premioGrupo: new FormControl('', [Validators.required, Validators.maxLength(90)]),
      ligaGrupo: new FormControl('', [Validators.required]),
    });

    this.buscaLigasAtivas();
  }

  submit() {
    if (this.criarGrupoForm.valid) {
      this.enviaCadastroGrupo();
    }
  }

  buscaLigasAtivas() {
    this.idUsuarioLogado = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_ID_USUARIO_LOGADO);
    this.appService.getListarLigasAtivasUsuarioId(this.idUsuarioLogado).subscribe(
      response => {
        this.ligasAtivas = [];
        const lista = response.body;
        lista.forEach(element => {
          this.ligasAtivas.push(
            {
              idLiga: element.idLiga,
              nomeLiga: element.nomeLiga,
              urlImagem: element.urlImagem
            }
          );
        });
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  setValue(e) {
    if (e.checked) {
      $('#label-tipo-grupo').css('color', 'lawngreen');
      $('#label-tipo-grupo').text('Privado');
      this.tipoGrupo = true;
    } else {
      $('#label-tipo-grupo').css('color', 'lawngreen');
      $('#label-tipo-grupo').text('Público');
      this.tipoGrupo = false;
    }
  }

  enviaCadastroGrupo() {
    const grupo = this.preencheObjetoGrupo();

    this.appService.postCadastrarGrupo(grupo).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.router.navigate(['tool/mais-grupos']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  preencheObjetoGrupo() {
    const grupo = {
      idLiga: this.criarGrupoForm.get('ligaGrupo').value,
      idUsuario: this.idUsuarioLogado,
      nomeGrupo: this.criarGrupoForm.get('nomeGrupo').value,
      descricaoGrupo: this.criarGrupoForm.get('descricaoGrupo').value,
      descricaoPremioGrupo: this.criarGrupoForm.get('premioGrupo').value,
      fechado: this.tipoGrupo
    };

    return grupo;
  }
}
