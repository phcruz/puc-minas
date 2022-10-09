export class SolicitacaoGrupoUsuario {

  idSolicitacaoGrupoUsuario: number;
  idGrupo: number;
  idLiga: number;
  idUsuarioAdmin: number;
  idUsuarioSolicitante: number;
  dataSolicitacao: Date;
  dataResposta: Date;
  permiteParticipar: boolean;
  ativo: boolean;
  // novos
  urlImagemAvatarUsuario: string;
  nomeUsuario: string;

  constructor(data: Partial<SolicitacaoGrupoUsuario>) {
    Object.assign(this, data);
  }
}
