(window.webpackJsonp=window.webpackJsonp||[]).push([[27],{NLo1:function(i,o,r){"use strict";r.r(o),o.default=""},Q5fh:function(i,o,r){"use strict";r.r(o),o.default='<div class="min-height-500">\r\n  <div *ngFor="let item of gruposImportar">\r\n    <div class="row margin-top-10" (click)="openDialogConfirmacaoImportarGrupo(item)">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8 click">\r\n\r\n        <mat-card>\r\n          <mat-card-content class="font-size-16">\r\n\r\n            <div class="row">\r\n              <div class="col-2">\r\n                <img class="img-60" src="{{item.urlImagem}}">\r\n              </div>\r\n              <div class="col-7">\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label>{{item.nomeGrupo}}</label>\r\n                  </div>\r\n                </div>\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label class="label-nome-liga">{{item.nomeLiga}}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n              <div class="col-3">\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label class="float-right">{{item.temporada}}</label>\r\n                  </div>\r\n                </div>\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <img class="img-30 float-right"\r\n                      src="assets/img/{{item.tipoGrupo}}">\r\n\r\n                    <img class="img-30 float-right"\r\n                      src="assets/img/{{item.tipoAdmin}}">\r\n                  </div>\r\n                </div>\r\n              </div>\r\n            </div>\r\n\r\n          </mat-card-content>\r\n        </mat-card>\r\n\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n  <div *ngIf="gruposImportar.length === 0">\r\n    <div class="row">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8">\r\n        <div class="div-label-tabs">\r\n          <label>Selecione mais grupos e participe das competi\xe7\xf5es.</label>\r\n        </div>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n</div>\r\n'},TKUT:function(i,o,r){"use strict";r.r(o);var a=r("mrSG"),s=r("8Y7J"),e=r("/mjz"),t=r("QktW"),c=r("Jm54"),d=r("TKFM"),n=r("9WgB"),l=r("5BJY"),p=r("KRgJ");let u=class{constructor(i,o,r,a,s){this.appService=i,this.dialogErroService=o,this.dialogConfirmacao=r,this.toastService=a,this.indicatorService=s,this.gruposImportar=[],this.idUsuarioLogado="0"}ngOnInit(){return a.a(this,void 0,void 0,(function*(){this.idUsuarioLogado=c.a.getInfosUsuarioLogado(d.a.PROPERT_ID_USUARIO_LOGADO),"0"!==this.idUsuarioLogado&&(this.getListaGruposParaImportar(this.idUsuarioLogado,d.a.INICIO_BUSCA),this.indicatorService.setExibeIconeMaisGrupos(!0))}))}getListaGruposParaImportar(i,o){this.appService.getListarGruposParaImportarPaginado(i,o).subscribe(i=>{this.gruposImportar=[],i.body.forEach(i=>{this.gruposImportar.push({idGrupo:i.idGrupo,idLiga:i.idLiga,idUsuario:i.idUsuario,nomeUsuarioAdmin:i.nomeUsuarioAdmin,descricaoGrupo:i.descricaoGrupo,descricaoPremioGrupo:i.descricaoPremioGrupo,mediaPontos:i.mediaPontos,quantidadeMembros:i.quantidadeMembros,tipoGrupo:!0===i.fechado?"fechado.png":"publico.png",urlImagem:i.urlImagem,nomeGrupo:i.nomeGrupo,nomeLiga:i.nomeLiga,temporada:i.temporada,fechado:i.fechado,pago:i.pago,valor:i.valor,tipoAdmin:i.idUsuario==this.idUsuarioLogado?"admin_grupo.png":"ic_blank.png"})})},i=>{this.dialogErroService.showMensagemErroDialog(i)})}openDialogConfirmacaoImportarGrupo(i){const o=i.fechado?"Solicita\xe7\xe3o de participa\xe7\xe3o":"Participar do grupo";let r=i.fechado?"Tem certeza que deseja enviar uma solicita\xe7\xe3o de participa\xe7\xe3o ao grupo ":"Tem certeza que deseja participar do grupo ";r+=i.nomeGrupo,this.dialogConfirmacao.openDialog(o,r),this.dialogConfirmacao.confirmed().subscribe(o=>{null!=o&&void 0!==o&&o.data&&(i.fechado?this.cadastrarSolicitacaoGrupoUsuarioWebService(i):this.cadastrarGrupoUsuarioWebService(i.idGrupo,i.idLiga))})}cadastrarSolicitacaoGrupoUsuarioWebService(i){const o=this.preencheObjetoSolicitacaoGrupoUsuario(i);this.appService.postCadastroSolicitacaoGrupoUsuario(o).subscribe(i=>{this.toastService.showToast(i.body),this.getListaGruposParaImportar(this.idUsuarioLogado,d.a.INICIO_BUSCA)},i=>{this.dialogErroService.showMensagemErroDialog(i)})}preencheObjetoSolicitacaoGrupoUsuario(i){return{idGrupo:i.idGrupo,idLiga:i.idLiga,idUsuarioAdmin:i.idUsuario,idUsuarioSolicitante:this.idUsuarioLogado}}cadastrarGrupoUsuarioWebService(i,o){const r=this.preencheObjetoGrupoUsuario(i,o);this.appService.postParticiparGrupoUsuario(r).subscribe(i=>{this.toastService.showToast(i.body),this.getListaGruposParaImportar(this.idUsuarioLogado,d.a.INICIO_BUSCA)},i=>{this.dialogErroService.showMensagemErroDialog(i)})}preencheObjetoGrupoUsuario(i,o){return{idGrupo:i,idLiga:o,idUsuario:this.idUsuarioLogado}}ngOnDestroy(){this.indicatorService.setExibeIconeMaisGrupos(!1)}};u.ctorParameters=()=>[{type:e.a},{type:t.a},{type:l.a},{type:n.a},{type:p.a}],u=a.b([Object(s.o)({selector:"app-mais-grupos",template:a.c(r("Q5fh")).default,styles:[a.c(r("NLo1")).default]})],u);var m=r("iInd");const g=[{path:"",component:u}];let v=class{};v=a.b([Object(s.L)({imports:[m.g.forChild(g)],exports:[m.g]})],v);var h=r("vWh4"),b=r("SVse");r.d(o,"MaisGruposModule",(function(){return G}));let G=class{};G=a.b([Object(s.L)({declarations:[u],imports:[b.b,v,h.a]})],G)}}]);