import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ToastService } from 'src/app/util/services/toast.service';
import { Router } from '@angular/router';
import { AppService } from 'src/app/util/services/app.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';

@Component({
  selector: 'app-alterar-senha',
  templateUrl: './alterar-senha.component.html',
  styleUrls: ['./alterar-senha.component.css']
})
export class AlterarSenhaComponent implements OnInit {

  alterarSenhaForm: FormGroup;

  constructor(private router: Router,
              private toastService: ToastService,
              private appService: AppService,
              private dialogErroService: DialogMensagemErroService) { }

  ngOnInit() {
    this.alterarSenhaForm = new FormGroup({
      senhaAtual: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]),
      senhaNova: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12),
                                      Validators.pattern('^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$')]),
      senhaNovaConfirmacao: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(12),
                                      Validators.pattern('^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$')]),
    });
  }

  submit() {
    if (this.alterarSenhaForm.valid) {
      if (this.validaCampos()) {
        const email = UtilMetodos.getInfosUsuarioLogado(Constants.PROPERT_EMAIL_USUARIO_LOGADO);
        this.buscarEmail(email);
      }
    }
  }

  validaCampos(): boolean {
    const senhaAtual = this.alterarSenhaForm.get('senhaAtual').value;
    const senhaNova = this.alterarSenhaForm.get('senhaNova').value;
    const senhaNovaConfirmacao = this.alterarSenhaForm.get('senhaNovaConfirmacao').value;

    if (senhaAtual === senhaNova) {
      this.toastService.showToast('A nova senha é igual a senha atual.');
      return false;
    }

    if (!UtilMetodos.validaSenha(senhaNova)) {
      this.toastService.showToast('Sua senha deve entre 6 e 12 caracteres. Use letras e números.');
      return false;
    }

    if (senhaNova !== senhaNovaConfirmacao) {
      this.toastService.showToast('Senhas não conferem');
      return false;
    }

    return true;
  }

  buscarEmail(email: string) {
    this.appService.buscaUsuarioEmail(email).subscribe(
      response => {
        this.verificaSenhaUsuario(response.body);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }

  verificaSenhaUsuario(data: any) {
    const senhaCripto = UtilMetodos.criptografaSenha(this.alterarSenhaForm.get('senhaAtual').value);
    if (data.senha === senhaCripto) {
      const usuario = {
        idUsuario: data.idUsuario,
        senha: UtilMetodos.criptografaSenha(this.alterarSenhaForm.get('senhaNova').value)
      };
      this.alterarSenhaUsuario(usuario);

    } else {
      this.toastService.showToast('Senha atual incorreta.');
    }
  }

  alterarSenhaUsuario(usuario: any) {
    this.appService.putAlterarSenhaUsuario(usuario).subscribe(
      response => {
        this.toastService.showToast(response.body);
        this.router.navigate(['tool/perfil-usuario']);
      }, err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
