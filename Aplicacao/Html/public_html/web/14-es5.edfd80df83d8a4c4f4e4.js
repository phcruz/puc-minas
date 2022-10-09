(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{"07tn":function(a,r,e){"use strict";e.r(r);var n=e("mrSG"),t=e("s7LF"),o=e("8Y7J"),s=e("9WgB"),i=e("iInd"),l=e("/mjz"),m=e("QktW"),c=e("Jm54"),h=e("TKFM");let d=class{constructor(a,r,e,n){this.router=a,this.toastService=r,this.appService=e,this.dialogErroService=n}ngOnInit(){this.alterarSenhaForm=new t.c({senhaAtual:new t.b("",[t.k.required,t.k.minLength(6),t.k.maxLength(12)]),senhaNova:new t.b("",[t.k.required,t.k.minLength(6),t.k.maxLength(12),t.k.pattern("^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$")]),senhaNovaConfirmacao:new t.b("",[t.k.required,t.k.minLength(6),t.k.maxLength(12),t.k.pattern("^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,18})$")])})}submit(){if(this.alterarSenhaForm.valid&&this.validaCampos()){const a=c.a.getInfosUsuarioLogado(h.a.PROPERT_EMAIL_USUARIO_LOGADO);this.buscarEmail(a)}}validaCampos(){const a=this.alterarSenhaForm.get("senhaAtual").value,r=this.alterarSenhaForm.get("senhaNova").value,e=this.alterarSenhaForm.get("senhaNovaConfirmacao").value;return a===r?(this.toastService.showToast("A nova senha \xe9 igual a senha atual."),!1):c.a.validaSenha(r)?r===e||(this.toastService.showToast("Senhas n\xe3o conferem"),!1):(this.toastService.showToast("Sua senha deve entre 6 e 12 caracteres. Use letras e n\xfameros."),!1)}buscarEmail(a){this.appService.buscaUsuarioEmail(a).subscribe(a=>{this.verificaSenhaUsuario(a.body)},a=>{this.dialogErroService.showMensagemErroDialog(a)})}verificaSenhaUsuario(a){const r=c.a.criptografaSenha(this.alterarSenhaForm.get("senhaAtual").value);if(a.senha===r){const r={idUsuario:a.idUsuario,senha:c.a.criptografaSenha(this.alterarSenhaForm.get("senhaNova").value)};this.alterarSenhaUsuario(r)}else this.toastService.showToast("Senha atual incorreta.")}alterarSenhaUsuario(a){this.appService.putAlterarSenhaUsuario(a).subscribe(a=>{this.toastService.showToast(a.body),this.router.navigate(["tool/perfil-usuario"])},a=>{this.dialogErroService.showMensagemErroDialog(a)})}};d.ctorParameters=()=>[{type:i.f},{type:s.a},{type:l.a},{type:m.a}],d=n.b([Object(o.o)({selector:"app-alterar-senha",template:n.c(e("oQun")).default,styles:[n.c(e("FeN0")).default]})],d);const u=[{path:"",component:d}];let p=class{};p=n.b([Object(o.L)({imports:[i.g.forChild(u)],exports:[i.g]})],p);var f=e("vWh4"),v=e("SVse");e.d(r,"AlterarSenhaModule",(function(){return g}));let g=class{};g=n.b([Object(o.L)({declarations:[d],imports:[v.b,p,f.a,t.e,t.j]})],g)},FeN0:function(a,r,e){"use strict";e.r(r),r.default=".mat-form-field {\r\n    width: 100%;\r\n  }\r\n\r\n  .button-enviar {\r\n    background-color: #D6D8D7;\r\n    width: 100%;\r\n    border-radius: 23px;\r\n    border: 2px solid #888888;\r\n  }\r\n\r\n  .button-enviar .mat-icon{\r\n    float: left;\r\n    margin-top: 10px;\r\n  }\r\n\r\n  .button-enviar .label-button{\r\n    margin: 3px;\r\n  }\r\n\r\n  ::ng-deep .mat-form-field-appearance-legacy .mat-form-field-label {\r\n    color: white !important;\r\n  }\r\n\r\n  ::ng-deep .mat-form-field-appearance-legacy .mat-form-field-underline {\r\n    background-color: white !important;\r\n  }\r\n\r\n  ::ng-deep .mat-form-field.mat-focused .mat-form-field-ripple {\r\n    background-color: #FAFAFA !important;\r\n  }\r\n\r\n  ::ng-deep .mat-form-field.mat-focused .mat-form-field-label {\r\n    color: #FAFAFA !important;\r\n  }\r\n"},oQun:function(a,r,e){"use strict";e.r(r),r.default='<div class="min-height-500">\r\n  <div class="row margin-top-10">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8 click">\r\n\r\n      <mat-card class="margin-3">\r\n        <mat-card-content class="font-size-16">\r\n          <div class="card-form">\r\n            <form [formGroup]="alterarSenhaForm" (ngSubmit)="submit()">\r\n              <p>\r\n                <mat-form-field>\r\n                  <input type="password" matInput placeholder="Senha atual" formControlName="senhaAtual" maxlength="12"/>\r\n                  <mat-error *ngIf="alterarSenhaForm.controls.senhaAtual.invalid"><strong>Informe a sua senha atual</strong></mat-error>\r\n                </mat-form-field>\r\n              </p>\r\n\r\n              <p>\r\n                <mat-form-field>\r\n                  <input type="password" matInput placeholder="Senha" formControlName="senhaNova" maxlength="12"/>\r\n                  <mat-error *ngIf="alterarSenhaForm.controls.senhaNova.invalid"><strong>A senha deve ter de 6 a 12 caracteres, com letras e n\xfameros</strong></mat-error>\r\n                </mat-form-field>\r\n              </p>\r\n\r\n              <p>\r\n                <mat-form-field>\r\n                  <input type="password" matInput placeholder="Confirme a senha" formControlName="senhaNovaConfirmacao" maxlength="12"/>\r\n                  <mat-error *ngIf="alterarSenhaForm.controls.senhaNovaConfirmacao.invalid"><strong>A senha deve ter de 6 a 12 caracteres, com letras e n\xfameros</strong></mat-error>\r\n                </mat-form-field>\r\n              </p>\r\n\r\n              <div class="button">\r\n                <button type="submit" mat-raised-button class="button-enviar" [disabled]="!alterarSenhaForm.valid">\r\n                  <mat-icon>send</mat-icon>\r\n                  <label class="label-button">ENVIAR</label>\r\n                </button>\r\n              </div>\r\n            </form>\r\n            </div>\r\n        </mat-card-content>\r\n      </mat-card>\r\n\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n</div>\r\n'}}]);