(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{VffR:function(r,n,i){"use strict";i.r(n);var a=i("mrSG"),l=i("8Y7J"),o=i("TKFM"),s=i("Jm54"),e=i("/mjz"),c=i("QktW"),t=i("9WgB"),d=i("KRgJ"),g=i("iInd");let v=class{constructor(r,n,i,a,l){this.router=r,this.appService=n,this.dialogErroService=i,this.toastService=a,this.indicatorService=l,this.listaRanking=[]}ngOnInit(){const r=s.a.getItemSessionStorage(o.a.GRUPO_SELECIONADO);r&&(this.grupoSelecionado=JSON.parse(r),this.grupoSelecionado.mediaPontos=(Math.round(100*this.grupoSelecionado.mediaPontos)/100).toFixed(1),this.getRanking(this.grupoSelecionado.idLiga,this.grupoSelecionado.idGrupo,o.a.INICIO_BUSCA,!0),this.indicatorService.setExibeIconeGrupoDetalhe(!0)),this.ouvinteVerificaRolagemPagina()}getRanking(r,n,i,a){this.appService.getListarRankingPorGrupoLigaPaginado(r,n,i).subscribe(r=>{const n=r.body;a&&(this.listaRanking=[]),n.length<30&&this.listaRanking.length>0&&this.toastService.showToast("Ranking completo"),n.forEach(r=>{this.listaRanking.push({posicao:r.posicao,pontos:r.pontos,nomeUsuario:r.nomeUsuario,urlImagem:r.urlImagem,acertoCincoPontos:r.acertoCincoPontos,acertoTresPontos:r.acertoTresPontos,acertoUmPonto:r.acertoUmPonto,acertoPontuou:r.acertoPontuou,idUsuario:r.idUsuario,idLiga:r.idLiga})})},r=>{this.dialogErroService.showMensagemErroDialog(r)})}ngOnDestroy(){s.a.getItemSessionStorage(o.a.GRUPO_SELECIONADO),this.indicatorService.setExibeIconeGrupoDetalhe(!1)}ouvinteVerificaRolagemPagina(){this.indicatorService.realizaChamadaScroll.subscribe(r=>{r&&"/tool/detalhe-grupo"===r&&this.getRanking(this.grupoSelecionado.idLiga,this.grupoSelecionado.idGrupo,this.listaRanking.length,!1)})}abreTelaDetalheUsuario(r){s.a.setItemSessionStorage(o.a.USUARIO_RANKING_SELECIONADO,JSON.stringify(r)),this.router.navigate(["tool/detalhe-usuario"])}};v.ctorParameters=()=>[{type:g.f},{type:e.a},{type:c.a},{type:t.a},{type:d.a}],v=a.b([Object(l.o)({selector:"app-detalhe-grupo",template:a.c(i("ymoP")).default,styles:[a.c(i("wcnV")).default]})],v);const m=[{path:"",component:v}];let p=class{};p=a.b([Object(l.L)({imports:[g.g.forChild(m)],exports:[g.g]})],p);var b=i("vWh4"),u=i("SVse");i.d(n,"DetalheGrupoModule",(function(){return h}));let h=class{};h=a.b([Object(l.L)({declarations:[v],imports:[u.b,p,b.a]})],h)},wcnV:function(r,n,i){"use strict";i.r(n),n.default=".text-center {\r\n  text-align: center;\r\n}\r\n\r\n.div-dados-detalhes {\r\n  text-align: -webkit-center;\r\n  padding-top: 10px;\r\n}\r\n\r\n.margin-0 {\r\n  margin: 0px;\r\n}\r\n\r\n.box-pontos {\r\n  background-color: yellow;\r\n  border-radius: 15px;\r\n  color: black;\r\n  padding: 0px 8px 0px 8px;\r\n  float: right;\r\n  border: 1.5px solid white;\r\n}\r\n\r\n.padding-0 {\r\n  padding-left: 0px;\r\n}\r\n\r\n.padding-left-5 {\r\n  padding-left: 5px;\r\n}\r\n\r\n.padding-rigth {\r\n  padding-right: 5px;\r\n}\r\n\r\n.col-style {\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-align: center;\r\n          align-items: center;\r\n  padding-right: 5px;\r\n}\r\n\r\n.label-posicao {\r\n  float: left;\r\n  margin-right: 10px;\r\n}\r\n\r\n.label-acerto {\r\n  display: initial;\r\n  vertical-align: middle;\r\n}\r\n\r\n.margin-top {\r\n  margin-top: 7px;\r\n}\r\n\r\n"},ymoP:function(r,n,i){"use strict";i.r(n),n.default='<div class="min-height-500">\r\n  <div class="row">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8">\r\n      \x3c!----\x3e\r\n      <mat-card>\r\n        <mat-card-content class="font-size-16">\r\n          <div class="row div-dados-detalhes">\r\n            <div class="col-4">\r\n              <label class="font-size-15 font-white">M\xe9dia</label><br/>\r\n              <label class="size-x circle">{{grupoSelecionado.mediaPontos}}</label>\r\n            </div>\r\n            <div class="col-4">\r\n              <img class="img-110" src="{{grupoSelecionado.urlImagem}}">\r\n            </div>\r\n            <div class="col-4">\r\n              <label class="font-size-15 font-white">Membros</label><br/>\r\n              <label class="size-x circle">{{grupoSelecionado.quantidadeMembros}}</label>\r\n            </div>\r\n          </div>\r\n          <div class="row text-center">\r\n            <div class="col-12">\r\n              <label class="font-size-19 font-white">{{grupoSelecionado.nomeGrupo}}</label>\r\n            </div>\r\n            <div class="col-12">\r\n              <label class="margin-0">{{grupoSelecionado.descricaoGrupo}}</label><br/>\r\n              <label class="margin-0">Pr\xeamio: {{grupoSelecionado.descricaoPremioGrupo}}</label>\r\n            </div>\r\n          </div>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n\r\n  \x3c!--card grupos--\x3e\r\n  <div class="row margin-top-10">\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n    <div class="col-lg-6 col-md-8 col-sm-8">\r\n      <mat-card class="paddingtop-bottom-0">\r\n        <mat-card-content class="font-size-16">\r\n          <Label>Ranking</Label>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n  </div>\r\n\r\n  \x3c!--teste--\x3e\r\n  <div>\r\n    <div *ngFor="let item of listaRanking">\r\n      <div class="row margin-top-10">\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n        <div class="col-lg-6 col-md-8 col-sm-8">\r\n          <mat-card class="click" (click)="abreTelaDetalheUsuario(item)">\r\n            <mat-card-content class="font-size-16">\r\n              <div class="row">\r\n                <div class="col-3 col-style">\r\n                  <label class="label-posicao">{{item.posicao}}</label>\r\n                  <img class="img-60" src="{{item.urlImagem}}" />\r\n                </div>\r\n                <div class="col-7">\r\n                  <div class="row">\r\n                    <div class="col-12 padding-left-5">\r\n                      <label>{{item.nomeUsuario}}</label>\r\n                    </div>\r\n                  </div>\r\n                  <div class="row margin-top">\r\n                    <div class="col-3 padding-left-5">\r\n                      <div class="row">\r\n                        <div class="col-6 col-style">\r\n                          <img class="img-20" src="assets/img/ic_medalha_ouro.png" />\r\n                        </div>\r\n                        <div class="col-6 padding-0">\r\n                          <label class="label-acerto">{{item.acertoCincoPontos}}</label>\r\n                        </div>\r\n                      </div>\r\n                    </div>\r\n                    <div class="col-3 padding-left-5">\r\n                      <div class="row">\r\n                        <div class="col-6 col-style">\r\n                          <img class="img-20" src="assets/img/ic_medalha_prata.png" />\r\n                        </div>\r\n                        <div class="col-6 padding-0">\r\n                          <label class="label-acerto">{{item.acertoTresPontos}}</label>\r\n                        </div>\r\n                      </div>\r\n                    </div>\r\n                    <div class="col-3 padding-left-5">\r\n                      <div class="row">\r\n                        <div class="col-6 col-style">\r\n                          <img class="img-20" src="assets/img/ic_medalha_bronze.png" />\r\n                        </div>\r\n                        <div class="col-6 padding-0">\r\n                          <label class="label-acerto">{{item.acertoUmPonto}}</label>\r\n                        </div>\r\n                      </div>\r\n                    </div>\r\n                    <div class="col-3 padding-left-5">\r\n                      <div class="row">\r\n                        <div class="col-6 col-style">\r\n                          <img class="img-20" src="assets/img/logo_app.png" />\r\n                        </div>\r\n                        <div class="col-6 padding-0">\r\n                          <label class="label-acerto">{{item.acertoPontuou}}</label>\r\n                        </div>\r\n                      </div>\r\n                    </div>\r\n                  </div>\r\n                </div>\r\n                <div class="col-2">\r\n                  <label class="box-pontos">{{item.pontos}}</label>\r\n                </div>\r\n              </div>\r\n            </mat-card-content>\r\n          </mat-card>\r\n        </div>\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      </div>\r\n    </div>\r\n    <div *ngIf="listaRanking.length === 0" class="min-height-500">\r\n      <div class="row">\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n        <div class="col-lg-6 col-md-8 col-sm-8">\r\n          <div class="div-label-tabs">\r\n            <label>Nenhum ranking dispon\xedvel.</label>\r\n          </div>\r\n        </div>\r\n        <div class="col-lg-3 col-md-2 col-sm-2"></div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>\r\n\r\n\r\n'}}]);