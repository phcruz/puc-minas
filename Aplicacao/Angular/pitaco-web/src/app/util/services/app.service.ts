import { UtilMetodos } from 'src/app/util/util-metodos';
import { Constants } from 'src/app/util/constants';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { HeaderAuthorizationService } from './header-authorization.service';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  //private urlBase = `${environment.apiBaseUrl}`;
  private urlBase = '/api';

  constructor(private http: HttpClient,
              private headerAuthorization: HeaderAuthorizationService) { }

  /* palpite */
  public postCadastraPalpitePartida(palpite: any): Observable<any> {
    return this.http.post<any>(this.urlBase + '/palpite/cadastro', palpite,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* usuario */
  public buscaUsuarioEmail(email: string): Observable<any> {
    const httpParams = new HttpParams().append('email', email);
    return this.http.get<any>(this.urlBase + '/usuario/buscarEmailUsuario',
          { headers: this.headerAuthorization.createHeadersPublic(), params: httpParams, observe: 'response' });
  }

  public postLoginUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(this.urlBase + '/usuario/login', usuario,
          { headers: this.headerAuthorization.createHeadersPublic(), observe: 'response' });
  }

  public postCadastraUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(this.urlBase + '/usuario/cadastro', usuario,
          { headers: this.headerAuthorization.createHeadersPublic(), observe: 'response' });
  }

  public putAtualizarUsuario(usuarioAtualiza: any): Observable<any> {
    return this.http.put<any>(this.urlBase + '/usuario/atualizarUsuario', usuarioAtualiza,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public putAlterarSenhaUsuario(usuario: any): Observable<any> {
    return this.http.put<any>(this.urlBase + '/usuario/alterarSenhaUsuario', usuario,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarUsuarioPorNome(nomeUsuario, inicio, idGrupo): Observable<any> {
    return this.http.get<any>(this.urlBase + '/usuario/listarUsuarioPorNome/' + nomeUsuario + '/' + inicio + '/' + idGrupo,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getRecuperaSenhaUsuario(emailUsuario): Observable<any> {
    const httpParams = new HttpParams().append('email', emailUsuario);
    return this.http.get<any>(this.urlBase + '/usuario/recuperaSenhaUsuario',
          { headers: this.headerAuthorization.createHeadersPublic(), params: httpParams, observe: 'response' });
  }

  /* partida */
  public getListarPartidasHojeLigasUsuarioPaginado(idUsuarioLogado, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/partida/listarPartidasHojeLigasUsuarioPaginado/' + idUsuarioLogado + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' }).pipe(retry(1), catchError(this.handleError));
  }

  public getListarPartidasLigaUsuarioPaginado(idUsuarioLogado, idLiga, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/partida/listarPartidasLigaUsuarioPaginado/' + idUsuarioLogado + '/' + idLiga + '/'  + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getTotalPartidasDiaAnterior(): Observable<any> {
    return this.http.get<any>(this.urlBase + '/partida/totalPartidasDiaAnterior',
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarPalpitePartidaUsuarioDetalhePaginado(idUsuario, idLiga, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/partida/listarPalpitePartidaUsuarioDetalhePaginado/'
                                          + idUsuario + '/' + idLiga + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarPalpitePartidaUsuarioPerfilPaginado(idUsuario, inicio): Observable<any> {
    const idLiga = 0;
    return this.http.get<any>(this.urlBase + '/partida/listarPalpitePartidaUsuarioPerfilPaginado/'
                                          + idUsuario + '/' + idLiga + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarHistoricoPartidasEquipe(idLiga, idEquipe, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/partida/listarHistoricoPartidasEquipe/' + idLiga + '/' + idEquipe + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* liga */
  public getListarLigasUsuarioIdPaginado(idUsuarioLogado, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/liga/listarLigasUsuarioIdPaginado/' + idUsuarioLogado + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListaClassificacaoGeralLiga(idLiga, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/liga/classificacaoGeral/' + idLiga + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarLigasAtivasUsuario(idUsuarioLogado): Observable<any> {
    return this.http.get<any>(this.urlBase + '/liga/listarLigasAtivasUsuario/' + idUsuarioLogado,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarLigasAtivasUsuarioId(idUsuarioLogado): Observable<any> {
    return this.http.get<any>(this.urlBase + '/liga/listarLigasUsuarioId/' + idUsuarioLogado,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarLigasParaImportarPaginado(idUsuarioLogado, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/liga/listarLigasParaImportarPaginado/' + idUsuarioLogado + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public postImportarLigaUsuario(ligaUsuario): Observable<any> {
    return this.http.post<any>(this.urlBase + '/liga/cadastroLigaUsuario', ligaUsuario,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* grupos */
  public getListarGruposUsuarioIdPaginado(idUsuarioLogado, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/grupo/listarGruposUsuarioIdPaginado/' + idUsuarioLogado + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarGruposLigaUsuarioIdPaginado(idUsuario, idLiga, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/grupo/listarGruposLigaUsuarioIdPaginado/' + idUsuario + '/' + idLiga + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarGruposParaImportarPaginado(idUsuarioLogado, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/grupo/listarGruposParaImportarPaginado/' + idUsuarioLogado + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public postCadastroSolicitacaoGrupoUsuario(solicitacao): Observable<any> {
    return this.http.post<any>(this.urlBase + '/grupo/cadastroSolicitacaoGrupoUsuario', solicitacao,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public postParticiparGrupoUsuario(grupoUsuario): Observable<any> {
    return this.http.post<any>(this.urlBase + '/grupo/cadastroGrupoUsuario', grupoUsuario,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public postCadastrarGrupo(grupo): Observable<any> {
    return this.http.post<any>(this.urlBase + '/grupo/cadastroGrupo', grupo,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public putEditarGrupo(grupo): Observable<any> {
    return this.http.put<any>(this.urlBase + '/grupo/edicaoGrupo', grupo,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getEnviaConviteAmigo(email, nomeUsuario): Observable<any> {
    return this.http.get<any>(this.urlBase + '/grupo/enviaConviteAmigo/' + email + '/' + nomeUsuario,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarSolicitacaoGrupo(idGrupo, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/grupo/listarSolicitacaoGrupoPaginado/' + idGrupo + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public putAlteraSolicitacaoGrupoUsuario(solicitacao): Observable<any> {
    return this.http.put<any>(this.urlBase + '/grupo/alteraSolicitacaoGrupoUsuario', solicitacao,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* ranking */
  public getListarRankingPorLigaPaginado(idLiga, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/ranking/listarRankingPorLigaPaginado/' + idLiga + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getBuscarRankingGlobalUsuarioId(idUsuarioLogado): Promise<any> {
    return this.http.get<any>(this.urlBase + '/ranking/buscarRankingGlobalUsuarioId/' + idUsuarioLogado,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' }).toPromise();
  }

  public getListarRankingPorGrupoLigaPaginado(idLiga, idGrupo, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/ranking/listarRankingPorGrupoLigaPaginado/' + idLiga + '/' + idGrupo + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarRankingUsuarioPorPalpite(idPartida, placarPalpite, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/ranking/listarRankingUsuarioPorPalpite/' + idPartida + '/' + placarPalpite + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* pontuador da rodada */
  public getListarPontuadorRodada(inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/pontuadorRodada/listarPontuadorRodada/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* plano de fundo */
  public getPlanoFundo(): Observable<any> {
    return this.http.get<any>(this.urlBase + '/planoFundo/imagem',
          { headers: this.headerAuthorization.createHeadersPublic(), observe: 'response' });
  }

  /* avatar */
  public getAvatares(inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/avatar/listaAvataresPaginado/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public putAtualizaAvatarUsuario(idAvatar, idUsuarioLogado): Observable<any> {
    return this.http.put<any>(this.urlBase + '/avatar/alterarAvatar/' + idAvatar + '/' + idUsuarioLogado, null,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* indicacao de liga */
  public postCadastraIndicacaoLiga(indicacaoLiga): Observable<any> {
    return this.http.post<any>(this.urlBase + '/indicacaoLiga/cadastro', indicacaoLiga,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  /* estatisticas */
  public getBuscarEstatisticasPartida(idPartida): Observable<any> {
    return this.http.get<any>(this.urlBase + '/estatistica/estatisticaPartida/' + idPartida,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  public getListarEstatisticaPlacarPartidaId(idPartida, inicio): Observable<any> {
    return this.http.get<any>(this.urlBase + '/estatistica/estatisticaPlacar/' + idPartida + '/' + inicio,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  // ativar app heroku
  public getAtivarAppHeroku(): Observable<any> {
    return this.http.get<any>(this.urlBase + '/appHeroku/ativar',
          { headers: this.headerAuthorization.createHeadersPublic(), observe: 'response' });
  }

  // buscar dados da artilharia da liga
  public getArtilhariaLiga(idLiga: string): Observable<any> {
    return this.http.get<any>(this.urlBase + '/artilharia/listar/' + idLiga,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  // controlador de acesso
  public postAcessoPlataforma(deviceInfo: any): Observable<any> {
    const acesso = {
      idUsuario: UtilMetodos.getIdUsuarioTokenJWT(),
      plataforma: Constants.ACESSO_PLATAFORMA,
      dados: deviceInfo
    };
    return this.http.post<any>(this.urlBase + '/acesso/plataforma', acesso,
          { headers: this.headerAuthorization.createHeaders(), observe: 'response' });
  }

  handleError(err: HttpErrorResponse) {
    return throwError(err);
  }
}
