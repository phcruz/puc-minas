import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/util/services/toast.service';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import * as $ from 'jquery';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';

@Component({
  selector: 'app-editar-grupo',
  templateUrl: './editar-grupo.component.html',
  styleUrls: ['./editar-grupo.component.css']
})
export class EditarGrupoComponent implements OnInit {

  editarGrupoForm: FormGroup;
  grupoSelecionado: any;
  tipoGrupoEdit = false;
  idUsuarioLogado = '0';

  constructor(private router: Router,
              private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.editarGrupoForm = new FormGroup({
      nomeGrupoEdit: new FormControl('', [Validators.required, Validators.maxLength(30)]),
      descricaoGrupoEdit: new FormControl('', [Validators.required, Validators.maxLength(90)]),
      premioGrupoEdit: new FormControl('', [Validators.required, Validators.maxLength(90)]),
    });

    const grupo = UtilMetodos.getItemSessionStorage(Constants.GRUPO_SELECIONADO);
    if (grupo) {
      this.grupoSelecionado = JSON.parse(grupo);
      this.preencheDadosTela();
    }
  }

  submit() {
    if (this.editarGrupoForm.valid) {
      this.enviaEdicaoGrupo();
    }
  }

  setValue(e) {
    if (e.checked) {
      $('#label-tipo-grupo-edit').css('color', 'lawngreen');
      $('#label-tipo-grupo-edit').text('Privado');
      this.tipoGrupoEdit = true;
    } else {
      $('#label-tipo-grupo-edit').css('color', 'lawngreen');
      $('#label-tipo-grupo-edit').text('Público');
      this.tipoGrupoEdit = false;
    }
  }

  preencheDadosTela() {
    this.editarGrupoForm.controls['nomeGrupoEdit'].setValue(this.grupoSelecionado.nomeGrupo);
    this.editarGrupoForm.controls['descricaoGrupoEdit'].setValue(this.grupoSelecionado.descricaoGrupo);
    this.editarGrupoForm.controls['premioGrupoEdit'].setValue(this.grupoSelecionado.descricaoPremioGrupo);
    this.tipoGrupoEdit = this.grupoSelecionado.fechado;
  }

  enviaEdicaoGrupo() {
    this.alteraObjetoGrupo();
    this.appService.putEditarGrupo(this.grupoSelecionado).subscribe(
      response => {
        this.toastService.showToast(response.body);
        UtilMetodos.setItemSessionStorage(Constants.GRUPO_SELECIONADO, JSON.stringify(this.grupoSelecionado));
        this.router.navigate(['tool/detalhe-grupo']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  alteraObjetoGrupo() {
    this.grupoSelecionado.nomeGrupo = this.editarGrupoForm.get('nomeGrupoEdit').value;
    this.grupoSelecionado.descricaoGrupo = this.editarGrupoForm.get('descricaoGrupoEdit').value;
    this.grupoSelecionado.descricaoPremioGrupo = this.editarGrupoForm.get('premioGrupoEdit').value;
    this.grupoSelecionado.fechado = this.tipoGrupoEdit;
  }
}
