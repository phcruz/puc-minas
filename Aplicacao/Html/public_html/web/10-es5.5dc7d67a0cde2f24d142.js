(window.webpackJsonp=window.webpackJsonp||[]).push([[10],{"7oTW":function(i,a,r){"use strict";r.r(a),a.default=".label-pais-liga {\r\n  margin: 0; margin-top: 4px;\r\n}\r\n\r\n.fab-container {\r\n  position: fixed;\r\n  bottom: 15px;\r\n  right: 15px;\r\n  z-index: 1;\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: reverse;\r\n          flex-direction: column-reverse;\r\n  -webkit-box-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.div-fab {\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: reverse;\r\n          flex-direction: column-reverse;\r\n  -webkit-box-align: center;\r\n          align-items: center;\r\n  margin-bottom: 5px;\r\n}\r\n\r\n.button-float {\r\n  background-color: orangered;\r\n}\r\n"},"Qh+d":function(i,a,r){"use strict";r.r(a);var o=r("mrSG"),s=r("8Y7J"),e=r("iInd"),t=r("TKFM"),n=r("KRgJ"),l=r("/mjz"),c=r("Jm54"),d=r("QktW"),g=r("GS7A"),m=r("5BJY"),b=r("9WgB");let v=class{constructor(i,a,r,o,s,e){this.appService=i,this.router=a,this.indicatorService=r,this.dialogErroService=o,this.dialogConfirmacao=s,this.toastService=e,this.ligasUsuario=[],this.idUsuarioLogado="0",this.isSingleClick=!0}ngOnInit(){this.idUsuarioLogado=c.a.getInfosUsuarioLogado(t.a.PROPERT_ID_USUARIO_LOGADO),"0"!==this.idUsuarioLogado&&this.getLigasUsuario(this.idUsuarioLogado,t.a.INICIO_BUSCA)}getLigasUsuario(i,a){this.appService.getListarLigasUsuarioIdPaginado(i,a).subscribe(i=>{this.ligasUsuario=[],this.ligasUsuario=i.body},i=>{this.dialogErroService.showMensagemErroDialog(i)})}abreTela(i){this.isSingleClick=!0,setTimeout(()=>{this.isSingleClick&&(c.a.setItemSessionStorage(t.a.ID_LIGA,i),this.indicatorService.setExibeIconeClassificacaoGeral(!0),this.router.navigate(["tool/partidas-liga"]))},250)}onDoubleClick(i){this.isSingleClick=!1,this.openDialogConfirmacaoImportarLiga(i)}openDialogConfirmacaoImportarLiga(i){const a="Deseja realmente sair da liga "+i.nomeLiga+"?";this.dialogConfirmacao.openDialog(a,""),this.dialogConfirmacao.confirmed().subscribe(a=>{if(null!=a&&void 0!==a&&a.data){const a=this.preencheObjetoLigaUsuario(i.idLiga);this.postImportarLigaUsuario(a)}})}preencheObjetoLigaUsuario(i){return{idLiga:i,idUsuario:this.idUsuarioLogado}}postImportarLigaUsuario(i){this.appService.postImportarLigaUsuario(i).subscribe(i=>{const a=i.body;this.toastService.showToast(a),this.getLigasUsuario(this.idUsuarioLogado,t.a.INICIO_BUSCA)},i=>{this.dialogErroService.showMensagemErroDialog(i)})}};v.ctorParameters=()=>[{type:l.a},{type:e.f},{type:n.a},{type:d.a},{type:m.a},{type:b.a}],v=o.b([Object(s.o)({selector:"app-ligas",template:o.c(r("Vjql")).default,animations:[Object(g.n)("fadeIn",[Object(g.m)("void => *",[Object(g.l)({opacity:0}),Object(g.e)(2e3,Object(g.l)({opacity:1}))])])],styles:[o.c(r("7oTW")).default]})],v);const p=[{path:"",component:v}];let u=class{};u=o.b([Object(s.L)({imports:[e.g.forChild(p)],exports:[e.g]})],u);var h=r("vWh4"),f=r("SVse");r.d(a,"LigasModule",(function(){return L}));let L=class{};L=o.b([Object(s.L)({declarations:[v],imports:[f.b,u,h.a]})],L)},Vjql:function(i,a,r){"use strict";r.r(a),a.default='<div class="min-height-500-app">\r\n  <div *ngFor="let item of ligasUsuario">\r\n    <div class="row" (click)="abreTela(item.idLiga)" (dblclick)="onDoubleClick(item)">\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n        <div class="col-lg-6 col-md-8 col-sm-8 margin-top-5">\r\n          <mat-card class="click">\r\n            <mat-card-content class="font-size-16">\r\n              <div class="row">\r\n                <div class="col-2">\r\n                  <img class="img-60" src="{{item.urlImagem}}">\r\n                </div>\r\n                <div class="col-7">\r\n                  <div class="row">\r\n                    <div class="col-12">\r\n                      <label>{{item.nomeLiga}}</label>\r\n                    </div>\r\n                  </div>\r\n                  <div class="row">\r\n                    <div class="col-12">\r\n                      <label styclassle="label-pais-liga">{{item.paisLiga}}</label>\r\n                    </div>\r\n                  </div>\r\n                </div>\r\n                <div class="col-3">\r\n                  <label class="float-right">{{item.temporada}}</label>\r\n                </div>\r\n              </div>\r\n            </mat-card-content>\r\n          </mat-card>\r\n        </div>\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      </div>\r\n    </div>\r\n    <div *ngIf="ligasUsuario.length === 0">\r\n      <div class="row">\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n        <div class="col-lg-6 col-md-8 col-sm-8">\r\n          <div class="div-label-tabs">\r\n            <label>Selecione mais ligas e participe das competi\xe7\xf5es.</label>\r\n          </div>\r\n        </div>\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      </div>\r\n    </div>\r\n    <div class="fab-container div-fab" @fadeIn>\r\n      <button routerLink="../../tool/mais-ligas" class="button-float" mat-fab>\r\n        <mat-icon class="icon">add</mat-icon>\r\n      </button>\r\n    </div>\r\n</div>\r\n'}}]);