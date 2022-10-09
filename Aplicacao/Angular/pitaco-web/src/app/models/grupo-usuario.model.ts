export class GrupoUsuario {

  idGrupoUsuario: number;
  idGrupo: number;
  idLiga: number;
  idUsuario: number;
  dataCadastro: Date;
  dataAlteracao: Date;
  ativo: boolean;

  constructor(data: Partial<GrupoUsuario>) {
    Object.assign(this, data);
  }
}
