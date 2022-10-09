(window.webpackJsonp=window.webpackJsonp||[]).push([[31],{U4ql:function(a,i,r){"use strict";r.r(i),i.default='<div class="min-height-500">\r\n  <div class="row" style="margin-top: -1px;">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8 background-header-detalhe-usuario" id="div-usuario-detalhe">\r\n      \x3c!----\x3e\r\n      <div class="row div-dados-detalhes">\r\n        <div class="col-4">\r\n          <label class="font-size-15 font-white">Pontos</label><br />\r\n          <label class="size-x circle">{{ valueRanking.pontos }}</label>\r\n        </div>\r\n        <div class="col-4">\r\n          <img class="img-110" src="{{ valueRanking.urlImagem }}" />\r\n        </div>\r\n        <div class="col-4">\r\n          <label class="font-size-15 font-white">Rank</label><br />\r\n          <label class="size-x circle">{{ valueRanking.posicao }}</label>\r\n        </div>\r\n      </div>\r\n      <div class="row text-center">\r\n        <div class="col-12">\r\n          <label class="font-size-19 font-white">{{\r\n            valueRanking.nomeUsuario\r\n          }}</label>\r\n        </div>\r\n      </div>\r\n\r\n      <div class="row text-center">\r\n        <div class="col-12">\r\n          <img class="img-20" src="assets/img/ic_medalha_ouro.png" />\r\n          <label class="label-dados">{{\r\n            valueRanking.acertoCincoPontos\r\n          }}</label>\r\n\r\n          <img class="img-20" src="assets/img/ic_medalha_prata.png" />\r\n          <label class="label-dados">{{ valueRanking.acertoTresPontos }}</label>\r\n\r\n          <img class="img-20" src="assets/img/ic_medalha_bronze.png" />\r\n          <label class="label-dados">{{ valueRanking.acertoUmPonto }}</label>\r\n\r\n          <img class="img-20" src="assets/img/logo_app.png" />\r\n          <label class="label-dados">{{ valueRanking.acertoPontuou }}</label>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n\r\n  \x3c!--card grupos--\x3e\r\n  <div class="row margin-top-10">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8">\r\n      <mat-card class="paddingtop-bottom-0">\r\n        <mat-card-content class="font-size-16">\r\n          <Label>Grupos</Label>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n  \x3c!--lista de grupos--\x3e\r\n  <div *ngFor="let item of gruposUsuario">\r\n    <div class="row margin-top-10">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8 click">\r\n        <mat-card (click)="abreTelaGrupoDetalhe(item)">\r\n          <mat-card-content class="font-size-16">\r\n            <div class="row">\r\n              <div class="col-2">\r\n                <img class="img-60" src="{{ item.urlImagem }}" />\r\n              </div>\r\n              <div class="col-7">\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label>{{ item.nomeGrupo }}</label>\r\n                  </div>\r\n                </div>\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label class="label-nome-liga">{{ item.nomeLiga }}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n              <div class="col-3">\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <label class="float-right">{{ item.temporada }}</label>\r\n                  </div>\r\n                </div>\r\n                <div class="row">\r\n                  <div class="col-12">\r\n                    <img class="img-30 float-right" src="assets/img/{{ item.tipoGrupo }}" />\r\n\r\n                    <img class="img-30 float-right" src="assets/img/{{ item.tipoAdmin }}" />\r\n                  </div>\r\n                </div>\r\n              </div>\r\n            </div>\r\n          </mat-card-content>\r\n        </mat-card>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n  <div *ngIf="gruposUsuario.length === 0">\r\n    <div class="row">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8">\r\n        <div class="div-label-detalhes">\r\n          <label>Selecione mais grupos e participe das competi\xe7\xf5es.</label>\r\n        </div>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n\r\n  \x3c!--card palpites--\x3e\r\n  <div class="row margin-top-10">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8">\r\n      <mat-card class="paddingtop-bottom-0">\r\n        <mat-card-content class="font-size-16">\r\n          <Label>Palpites</Label>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n  \x3c!--lista de partidas--\x3e\r\n  <div *ngFor="let item of partidasUsuario">\r\n    <div class="row margin-top-10">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8 click">\r\n        <mat-card (click)="abreTelaPalpitePartida(item)">\r\n          <mat-card-content class="font-size-16">\r\n            \x3c!-- dados da partida --\x3e\r\n            <div class="row">\r\n              <div class="col-5 text-start">\r\n                <label>{{ item.nomeMandante }}</label>\r\n              </div>\r\n              <div class="col-2">\r\n                <img class="img-30 margin-auto-display-block" src="{{ item.imgLogoLiga }}" />\r\n              </div>\r\n              <div class="col-5 text-end">\r\n                <label>{{ item.nomeVisitante }}</label>\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados de palpite e resultado --\x3e\r\n            <div class="row text-center">\r\n              <div class="col-3">\r\n                <img class="img-75 float-left" src="{{ item.imgLogoMandante }}" />\r\n              </div>\r\n\r\n              \x3c!--de o seu palpite--\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 1">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-md-12 col-sm-12">\r\n                    <label class="box background-palpite margin-top">D\xea o seu palpite</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- palpite antes do jogo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 2">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <label>{{ item.placarPalpite }}</label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <label></label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- sem palpite jogo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 3">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <label></label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <label>{{ item.placarPartida }}</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--pontos--\x3e\r\n                <div class="row">\r\n                  <div class="col-md-12 col-sm-12 div-label-pontos">\r\n                    <label class="box">{{ item.pontos }}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              \x3c!-- completo --\x3e\r\n              <div class="col-6" *ngIf="getItemViewType(item) === 4">\r\n                \x3c!-- icones de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_dados.png" />\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <img class="img-25 margin-auto-display-block" src="assets/img/ic_apito.png" />\r\n                  </div>\r\n                </div>\r\n                \x3c!-- dados de palpite e resultado --\x3e\r\n                <div class="row">\r\n                  <div class="col-6">\r\n                    <label>{{ item.placarPalpite }}</label>\r\n                  </div>\r\n                  <div class="col-6">\r\n                    <label>{{ item.placarPartida }}</label>\r\n                  </div>\r\n                </div>\r\n                \x3c!--pontos--\x3e\r\n                <div class="row">\r\n                  <div class="col-md-12 col-sm-12 div-label-pontos">\r\n                    <label class="box">{{ item.pontos }}</label>\r\n                  </div>\r\n                </div>\r\n              </div>\r\n\r\n              <div class="col-3">\r\n                <img class="img-75 float-right" src="{{ item.imgLogoVisitante }}" />\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados da partida --\x3e\r\n            <div class="row" *ngIf="item.localJogo !== \'JOGO ADIADO\'">\r\n              <div class="col-md-12 col-sm-12 text-center">\r\n                <label class="margin-0">{{ item.localJogoDataHora }}</label>\r\n                <label class="margin-0" *ngIf="item.tempoPartida !== \'\'">&nbsp;|</label>\r\n                <label class="margin-0 cor-green" *ngIf="\r\n                    item.tempoPartida !== \'\' &&\r\n                    item.tempoPartida !== \'Encerrado\'\r\n                  ">&nbsp;{{ item.tempoPartida }}</label>\r\n                <label class="margin-0 cor-red" *ngIf="\r\n                    item.tempoPartida !== \'\' &&\r\n                    item.tempoPartida === \'Encerrado\'\r\n                  ">&nbsp;{{ item.tempoPartida }}</label>\r\n              </div>\r\n            </div>\r\n            <div class="row" *ngIf="item.localJogo === \'JOGO ADIADO\'">\r\n              <div class="col-md-12 col-sm-12 label-jogo-adiado">\r\n                <label class="margin-0">{{ item.localJogo }}</label>\r\n              </div>\r\n            </div>\r\n\r\n            \x3c!-- dados de gols da partida --\x3e\r\n            <div class="row" *ngIf="\r\n                item.golsEquipeCasa !== null || item.golsEquipeVisitante != null\r\n              ">\r\n              <div class="col-5 text-start">\r\n                <label class="margin-bottom">{{ item.golsEquipeCasa }}</label>\r\n              </div>\r\n              <div class="col-2">\r\n                <img class="img-gols" src="assets/img/logo_app.png" />\r\n              </div>\r\n              <div class="col-5 text-end">\r\n                <label>{{ item.golsEquipeVisitante }}</label>\r\n              </div>\r\n            </div>\r\n          </mat-card-content>\r\n        </mat-card>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n  <div *ngIf="partidasUsuario.length === 0">\r\n    <div class="row">\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      <div class="col-lg-6 col-md-8 col-sm-8">\r\n        <div class="div-label-detalhes">\r\n          <label>Cadastre-se em mais ligas e palpite nas partidas.</label>\r\n        </div>\r\n      </div>\r\n      <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    </div>\r\n  </div>\r\n</div>\r\n'},"iu+g":function(a,i,r){"use strict";r.r(i),i.default=".padding-0 {\r\n  padding-left: 0px;\r\n}\r\n\r\n.padding-left-5 {\r\n  padding-left: 5px;\r\n}\r\n\r\n.label-dados {\r\n  vertical-align: -webkit-baseline-middle;\r\n  margin-left: 5px;\r\n  margin-right: 5px;\r\n  color: white;\r\n}\r\n\r\n.font-size-15 {\r\n  font-size: 15px;\r\n}\r\n\r\n.font-size-19 {\r\n  font-size: 19px;\r\n}\r\n\r\n.font-white {\r\n  color: white;\r\n}\r\n\r\n.text-center {\r\n  text-align: center;\r\n}\r\n\r\n.div-dados-detalhes {\r\n  text-align: -webkit-center;\r\n  padding-top: 10px;\r\n}\r\n\r\n.paddingtop-bottom-0 {\r\n  padding-bottom: 0px;\r\n  padding-top: 0px;\r\n}\r\n\r\n.box {\r\n  background-color: #FD8D00;\r\n  border-radius: 20px;\r\n  border: 1.5px solid white;\r\n  color: white;\r\n  padding: 2px;\r\n  padding-left: 8px;\r\n  padding-right: 8px;\r\n}\r\n\r\n.background-palpite {\r\n  background-color: orangered;\r\n}\r\n\r\n.img-gols {\r\n  width: 20px; margin: auto; display: block;\r\n}\r\n\r\n.text-start {\r\n  text-align: start;\r\n}\r\n\r\n.text-end {\r\n  text-align: end;\r\n}\r\n\r\n.text-center {\r\n  text-align: center;\r\n}\r\n\r\n.margin-0 {\r\n  margin: 0 0 0px;\r\n}\r\n\r\n.margin-bottom {\r\n  margin-bottom: 0px;\r\n}\r\n\r\n.div-label-pontos {\r\n  text-align: center; margin-top: -8px;\r\n}\r\n\r\n.margin-auto-display-block {\r\n  margin: auto;\r\n  display: block;\r\n}\r\n\r\n.margin-top {\r\n  margin-top: 8%;\r\n}\r\n"},v19g:function(a,i,r){"use strict";r.r(i);var n=r("mrSG"),s=r("8Y7J"),l=r("iInd"),e=r("/mjz"),o=r("TKFM"),t=r("EVdn"),d=r("Jm54"),c=r("QktW"),p=r("KRgJ"),m=r("9WgB");let g=class{constructor(a,i,r,n,s,l){this.activatedRoute=a,this.router=i,this.appService=r,this.toastService=n,this.dialogErroService=s,this.indicatorService=l,this.gruposUsuario=[],this.partidasUsuario=[],this.idUsuarioLogado="0",this.urlPlanoFundo=""}ngOnInit(){return n.a(this,void 0,void 0,(function*(){this.indicatorService.setExibeIconePerfilUsuario(!0),this.getPlanoFundo();const a=d.a.getItemSessionStorage(o.a.USUARIO_RANKING_SELECIONADO);a&&(this.valueRanking=JSON.parse(a),this.getGruposUsuario(this.valueRanking.idUsuario,o.a.INICIO_BUSCA),this.getPartidasPalpiteUsuario(this.valueRanking.idUsuario,o.a.INICIO_BUSCA,!0)),this.ouvinteVerificaRolagemPagina()}))}getPlanoFundo(){this.appService.getPlanoFundo().subscribe(a=>{this.urlPlanoFundo=a.body.urlImagem,t("#div-usuario-detalhe").css("background-image","url("+this.urlPlanoFundo+")"),t("#div-usuario-detalhe").css("background-size","cover")},a=>{this.dialogErroService.showMensagemErroDialog(a)})}getGruposUsuario(a,i){this.appService.getListarGruposUsuarioIdPaginado(a,i).subscribe(a=>{a.body.forEach(a=>{this.gruposUsuario.push({idGrupo:a.idGrupo,idLiga:a.idLiga,idUsuario:a.idUsuario,nomeUsuarioAdmin:a.nomeUsuarioAdmin,descricaoGrupo:a.descricaoGrupo,descricaoPremioGrupo:a.descricaoPremioGrupo,mediaPontos:a.mediaPontos,quantidadeMembros:a.quantidadeMembros,fechado:a.fechado,pago:a.pago,valor:a.valor,tipoGrupo:!0===a.fechado?"fechado.png":"publico.png",urlImagem:a.urlImagem,nomeGrupo:a.nomeGrupo,nomeLiga:a.nomeLiga,temporada:a.temporada,tipoAdmin:a.idUsuario==this.idUsuarioLogado?"admin_grupo.png":"ic_blank.png"})})},a=>{this.dialogErroService.showMensagemErroDialog(a)})}getPartidasPalpiteUsuario(a,i,r){this.appService.getListarPalpitePartidaUsuarioPerfilPaginado(a,i).subscribe(a=>{const i=a.body;r&&(this.partidasUsuario=[]),i.length<30&&this.partidasUsuario.length>0&&this.toastService.showToast("Lista partidas completa"),i.forEach(a=>{this.partidasUsuario.push({idPartida:a.idPartida,idLiga:a.idLiga,idMandante:a.mandante.idEquipe,idVisitante:a.visitante.idEquipe,imgLogoLiga:a.urlImagemLogoLiga,nomeMandante:a.mandante.nome,imgLogoMandante:a.mandante.urlImagemEquipe,nomeVisitante:a.visitante.nome,imgLogoVisitante:a.visitante.urlImagemEquipe,localJogo:a.localJogo,permiteAposta:a.permiteAposta,mostraPontuacao:a.mostraPontuacao,dataHoraJogoTexto:a.dataHoraJogoTexto,placarEquipeCasa:a.placarEquipeCasa,placarEquipeVisitante:a.placarEquipeVisitante,tempoPartida:null!==a.tempoPartida?a.tempoPartida:"",golsEquipeCasa:a.golsEquipeCasa,golsEquipeVisitante:a.golsEquipeVisitante,idPalpite:a.palpite.idPalpite,pontos:a.palpite.pontos<=1?a.palpite.pontos+" ponto":a.palpite.pontos+" pontos",localJogoDataHora:a.localJogo+" "+a.dataHoraJogoTexto,placarPartida:a.placarEquipeCasa+"x"+a.placarEquipeVisitante,placarPalpite:a.palpite.placarEquipeCasa+"x"+a.palpite.placarEquipeVisitante})})},a=>{this.dialogErroService.showMensagemErroDialog(a)})}ngOnDestroy(){this.indicatorService.setExibeIconePerfilUsuario(!1)}ouvinteVerificaRolagemPagina(){this.indicatorService.realizaChamadaScroll.subscribe(a=>{a&&"/tool/perfil-usuario"===a&&this.getPartidasPalpiteUsuario(this.valueRanking.idUsuario,this.partidasUsuario.length,!1)})}abreTelaGrupoDetalhe(a){d.a.setItemSessionStorage(o.a.GRUPO_SELECIONADO,JSON.stringify(a)),this.router.navigate(["tool/detalhe-grupo"])}abreTelaPalpitePartida(a){d.a.setItemSessionStorage(o.a.PARTIDA_SELECIONADA,JSON.stringify(a)),this.router.navigate(["tool/palpite-partida"])}getItemViewType(a){return a.localJogo===o.a.JOGO_ADIADO&&0===a.idPalpite?(a.permiteAposta=!0,a.mostraPontuacao=!1,o.a.FACA_SUA_APOSTA):a.localJogo===o.a.JOGO_ADIADO&&a.idPalpite>0?(a.permiteAposta=!0,a.mostraPontuacao=!1,o.a.PALPITE_ANTES_JOGO):a.permiteAposta&&0===a.idPalpite?o.a.FACA_SUA_APOSTA:a.permiteAposta&&a.idPalpite>0?o.a.PALPITE_ANTES_JOGO:a.permiteAposta||0!==a.idPalpite?!a.mostraPontuacao&&a.idPalpite>0?o.a.PALPITE_ANTES_JOGO:o.a.COMPLETO:o.a.SEM_PALPITE_JOGO}};g.ctorParameters=()=>[{type:l.a},{type:l.f},{type:e.a},{type:m.a},{type:c.a},{type:p.a}],g=n.b([Object(s.o)({selector:"app-perfil-usuario",template:n.c(r("U4ql")).default,styles:[n.c(r("iu+g")).default]})],g);const v=[{path:"",component:g}];let u=class{};u=n.b([Object(s.L)({imports:[l.g.forChild(v)],exports:[l.g]})],u);var b=r("vWh4"),x=r("SVse");r.d(i,"PerfilUsuarioModule",(function(){return h}));let h=class{};h=n.b([Object(s.L)({declarations:[g],imports:[x.b,u,b.a]})],h)}}]);