(window.webpackJsonp=window.webpackJsonp||[]).push([[30],{YNgM:function(a,i,r){"use strict";r.r(i),i.default=".box {\r\n  background-color: #FD8D00;\r\n  border-radius: 20px;\r\n  border: 1.5px solid white;\r\n  color: white;\r\n  padding: 2px;\r\n  padding-left: 8px;\r\n  padding-right: 8px;\r\n}\r\n\r\n.background-palpite {\r\n  background-color: orangered;\r\n}\r\n\r\n.img-gols {\r\n  width: 20px; margin: auto; display: block;\r\n}\r\n\r\n.text-start {\r\n  text-align: start;\r\n}\r\n\r\n.text-end {\r\n  text-align: end;\r\n}\r\n\r\n.text-center {\r\n  text-align: center;\r\n}\r\n\r\n.margin-0 {\r\n  margin: 0 0 0px;\r\n}\r\n\r\n.margin-bottom {\r\n  margin-bottom: 0px;\r\n}\r\n\r\n.div-label-pontos {\r\n  text-align: center; margin-top: -8px;\r\n}\r\n\r\n.margin-auto-display-block {\r\n  margin: auto;\r\n  display: block;\r\n}\r\n\r\n.margin-top {\r\n  margin-top: 8%;\r\n}\r\n"},l9bk:function(a,i,r){"use strict";r.r(i);var t=r("mrSG"),e=r("8Y7J"),n=r("TKFM"),l=r("KRgJ"),s=r("/mjz"),o=r("Jm54"),d=r("QktW"),c=r("iInd"),p=r("9WgB");let g=class{constructor(a,i,r,t,e){this.router=a,this.appService=i,this.toastService=r,this.indicatorService=t,this.dialogErroService=e,this.partidasLiga=[],this.idUsuarioLogado="0"}ngOnInit(){this.indicatorService.setExibeIconeClassificacaoGeral(!0),this.idLiga=o.a.getItemSessionStorage(n.a.ID_LIGA),this.idUsuarioLogado=o.a.getInfosUsuarioLogado(n.a.PROPERT_ID_USUARIO_LOGADO),null!==this.idLiga&&void 0!==this.idLiga&&null!==this.idUsuarioLogado&&void 0!==this.idUsuarioLogado&&this.getPartidasLiga(this.idUsuarioLogado,this.idLiga,n.a.INICIO_BUSCA,!0),this.ouvinteVerificaRolagemPagina()}getPartidasLiga(a,i,r,t){this.appService.getListarPartidasLigaUsuarioPaginado(a,i,r).subscribe(a=>{const i=a.body;t&&(this.partidasLiga=[]),i.length<30&&this.partidasLiga.length>0&&this.toastService.showToast("Lista de partidas completa"),i.forEach(a=>{this.partidasLiga.push({idPartida:a.idPartida,idLiga:a.idLiga,idMandante:a.mandante.idEquipe,idVisitante:a.visitante.idEquipe,imgLogoLiga:a.urlImagemLogoLiga,nomeMandante:a.mandante.nome,imgLogoMandante:a.mandante.urlImagemEquipe,nomeVisitante:a.visitante.nome,imgLogoVisitante:a.visitante.urlImagemEquipe,localJogo:a.localJogo,permiteAposta:a.permiteAposta,mostraPontuacao:a.mostraPontuacao,dataHoraJogoTexto:a.dataHoraJogoTexto,placarEquipeCasa:a.placarEquipeCasa,placarEquipeVisitante:a.placarEquipeVisitante,tempoPartida:a.tempoPartida,golsEquipeCasa:a.golsEquipeCasa,golsEquipeVisitante:a.golsEquipeVisitante,placarExtendidoEquipeCasa:a.placarExtendidoEquipeCasa,placarExtendidoEquipeVisitante:a.placarExtendidoEquipeVisitante,idPalpite:a.palpite.idPalpite,placarPalpiteCasa:a.palpite.placarEquipeCasa,placarPalpiteVisitante:a.palpite.placarEquipeVisitante,pontos:a.palpite.pontos<=1?a.palpite.pontos+" ponto":a.palpite.pontos+" pontos",localJogoDataHora:a.localJogo+" "+a.dataHoraJogoTexto+(null!==a.tempoPartida?" | "+a.tempoPartida:""),placarPartida:a.placarEquipeCasa+"x"+a.placarEquipeVisitante,placarPalpite:a.palpite.placarEquipeCasa+"x"+a.palpite.placarEquipeVisitante})})},a=>{this.dialogErroService.showMensagemErroDialog(a)})}ngOnDestroy(){this.indicatorService.setExibeIconeClassificacaoGeral(!1)}abreTelaPalpitePartida(a){o.a.setItemSessionStorage(n.a.PARTIDA_SELECIONADA,JSON.stringify(a)),this.router.navigate(["tool/palpite-partida"])}ouvinteVerificaRolagemPagina(){this.indicatorService.realizaChamadaScroll.subscribe(a=>{a&&"/tool/partidas-liga"===a&&this.getPartidasLiga(this.idUsuarioLogado,this.idLiga,this.partidasLiga.length,!1)})}getItemViewType(a){return a.localJogo===n.a.JOGO_ADIADO&&0===a.idPalpite?(a.permiteAposta=!0,a.mostraPontuacao=!1,n.a.FACA_SUA_APOSTA):a.localJogo===n.a.JOGO_ADIADO&&a.idPalpite>0?(a.permiteAposta=!0,a.mostraPontuacao=!1,n.a.PALPITE_ANTES_JOGO):a.permiteAposta&&0===a.idPalpite?n.a.FACA_SUA_APOSTA:a.permiteAposta&&a.idPalpite>0?n.a.PALPITE_ANTES_JOGO:a.permiteAposta||0!==a.idPalpite?!a.mostraPontuacao&&a.idPalpite>0?n.a.PALPITE_ANTES_JOGO:n.a.COMPLETO:n.a.SEM_PALPITE_JOGO}};g.ctorParameters=()=>[{type:c.f},{type:s.a},{type:p.a},{type:l.a},{type:d.a}],g=t.b([Object(e.o)({selector:"app-partidas-liga",template:t.c(r("sL2C")).default,styles:[t.c(r("YNgM")).default]})],g);const m=[{path:"",component:g}];let v=class{};v=t.b([Object(e.L)({imports:[c.g.forChild(m)],exports:[c.g]})],v);var u=r("vWh4"),b=r("SVse");r.d(i,"PartidasLigaModule",(function(){return x}));let x=class{};x=t.b([Object(e.L)({declarations:[g],imports:[b.b,v,u.a]})],x)},sL2C:function(a,i,r){"use strict";r.r(i),i.default='<div class="min-height-500">\r\n  <div *ngFor="let item of partidasLiga">\r\n    <div class="row margin-top-10">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8 click" (click)="abreTelaPalpitePartida(item)">\r\n\r\n        <mat-card>\r\n          <mat-card-content class="font-size-16">\r\n            \x3c!-- dados da partida --\x3e\r\n            <div class="row">\r\n              <div class="col-5 text-start">\r\n                <label>{{item.nomeMandante}}</label>\r\n              </div>\r\n              <div class="col-2">\r\n                <img class="img-30 margin-auto-display-block" src="{{item.imgLogoLiga}}"/>\r\n              </div>\r\n              <div class="col-5 text-end">\r\n                <label>{{item.nomeVisitante}}</label>\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados de palpite e resultado --\x3e\r\n            <div class="row text-center">\r\n              <div class="col-3">\r\n                <img class="img-75 float-left" src="{{item.imgLogoMandante}}" />\r\n              </div>\r\n\r\n              \x3c!--de o seu palpite--\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 1">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row" >\r\n                  <div class="col-md-12 col-sm-12">\r\n                  <label class="box background-palpite margin-top">D\xea o seu palpite</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- palpite antes do jogo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 2">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <label>{{item.placarPalpite}}</label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <label></label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- sem palpite jogo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 3">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <label></label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <label>{{item.placarPartida}}</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--penalidades--\x3e\r\n                <div class="row margin-bottom-5"\r\n                  *ngIf="item.placarExtendidoEquipeCasa !== null && item.placarExtendidoEquipeVisitante !== null">\r\n                  <div class="col-md-12 col-sm-12">\r\n                    <label>({{item.placarExtendidoEquipeCasa}}) p\xeanaltis ({{item.placarExtendidoEquipeVisitante}})</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--pontos--\x3e\r\n                <div class="row">\r\n                  <div class="col-md-12 col-sm-12 div-label-pontos">\r\n                  <label class="box">{{item.pontos}}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- completo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 4">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                  <label>{{item.placarPalpite}}</label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                  <label>{{item.placarPartida}}</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--penalidades--\x3e\r\n                <div class="row margin-bottom-5"\r\n                  *ngIf="item.placarExtendidoEquipeCasa !== null && item.placarExtendidoEquipeVisitante !== null">\r\n                  <div class="col-md-12 col-sm-12">\r\n                    <label>({{item.placarExtendidoEquipeCasa}}) p\xeanaltis ({{item.placarExtendidoEquipeVisitante}})</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--pontos--\x3e\r\n                <div class="row">\r\n                  <div class="col-md-12 col-sm-12 div-label-pontos">\r\n                  <label class="box">{{item.pontos}}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              <div class="col-3">\r\n                <img class="img-75 float-right" src="{{item.imgLogoVisitante}}"/>\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados da partida --\x3e\r\n            <div class="row" *ngIf="item.localJogo !== \'JOGO ADIADO\'">\r\n              <div class="col-md-12 col-sm-12 text-center">\r\n                <label class="margin-0">{{item.localJogoDataHora}}</label>\r\n              </div>\r\n            </div>\r\n            <div class="row" *ngIf="item.localJogo === \'JOGO ADIADO\'">\r\n              <div class="col-md-12 col-sm-12 label-jogo-adiado">\r\n                <label class="margin-0">{{item.localJogo}}</label>\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados de gols da partida --\x3e\r\n            <div class="row" *ngIf="item.golsEquipeCasa !== null || item.golsEquipeVisitante != null">\r\n              <div class="col-5 text-start">\r\n                <label class="margin-bottom">{{item.golsEquipeCasa}}</label>\r\n              </div>\r\n              <div class="col-2">\r\n                <img class="img-gols" src="assets/img/logo_app.png"/>\r\n              </div>\r\n              <div class="col-5 text-end">\r\n                <label>{{item.golsEquipeVisitante}}</label>\r\n              </div>\r\n            </div>\r\n\r\n          </mat-card-content>\r\n        </mat-card>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n  <div *ngIf="partidasLiga.length === 0">\r\n    <div class="row">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8">\r\n        <div class="div-label-tabs">\r\n          <label>Ops! Nenhum dado para exibir \u{1f615}.</label>\r\n        </div>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n\r\n</div>\r\n'}}]);