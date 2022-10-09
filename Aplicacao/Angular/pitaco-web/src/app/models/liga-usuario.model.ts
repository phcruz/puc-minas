export class LigaUsuario {

  idLigaUsuario: number;
  idLiga: number;
  idUsuario: number;
  dataCadastro: Date;
  dataAlteracao: Date;
  ativo: boolean;

  constructor(data: Partial<LigaUsuario>) {
    Object.assign(this, data);
  }
}
