import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IndicatorBehaviorService {

  public exibeIconeClassificacaoGeral = new BehaviorSubject<any>(false);
  public exibeIconeInfoLigas = new BehaviorSubject<any>(false);
  public idLigaRankingSelecionado = new BehaviorSubject<any>(0);
  public exibeIconePontuadores = new BehaviorSubject<any>(false);
  public alteraTabsViaDrawer = new BehaviorSubject<any>(10);
  public exibeIconeDrawermenu = new BehaviorSubject<any>(true);
  public exibeIconePerfilUsuario = new BehaviorSubject<any>(false);
  public dadosUsuarioLogado = new BehaviorSubject<any>(0);
  public exibeIconeGrupoDetalhe = new BehaviorSubject<any>(false);
  public exibeIconeMaisGrupos = new BehaviorSubject<any>(false);
  public exibeIconeDetalhesPalpites = new BehaviorSubject<any>(false);
  public realizaChamadaScroll = new BehaviorSubject<any>(null);

  constructor() { }

  setExibeIconeClassificacaoGeral(value: any) {
    this.exibeIconeClassificacaoGeral.next(value);
  }

  setExibeIconeInfoLigas(value: any) {
    this.exibeIconeInfoLigas.next(value);
  }

  setIdLigaRankingSelecionado(value: any) {
    this.idLigaRankingSelecionado.next(value);
  }

  setExibeIconePontuadores(value: any) {
    this.exibeIconePontuadores.next(value);
  }

  setAlteraTabsViaDrawer(value: any) {
    this.alteraTabsViaDrawer.next(value);
  }

  setExibeIconeDrawermenu(value: any) {
    this.exibeIconeDrawermenu.next(value);
  }

  setExibeIconePerfilUsuario(value: any) {
    this.exibeIconePerfilUsuario.next(value);
  }

  setSalvaDadosUsuarioLogado(value: any) {
    this.dadosUsuarioLogado.next(value);
  }

  setExibeIconeGrupoDetalhe(value: any) {
    this.exibeIconeGrupoDetalhe.next(value);
  }

  setExibeIconeMaisGrupos(value: any) {
    this.exibeIconeMaisGrupos.next(value);
  }

  setExibeIconeDetalhesPalpites(value: any) {
    this.exibeIconeDetalhesPalpites.next(value);
  }

  setRealizaChamadaScroll(value: any) {
    this.realizaChamadaScroll.next(value);
  }
}
