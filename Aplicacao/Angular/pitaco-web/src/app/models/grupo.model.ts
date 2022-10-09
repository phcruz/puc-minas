export class Grupo {

  idGrupo: number;
  idLiga: number;
  idUsuario: number;
  nomeUsuarioAdmin: string;
  nomeGrupo: string;
  nomeLiga: string;
  descricaoGrupo: string;
  descricaoPremioGrupo: string;
  temporada: string;
  urlImagem: string;
  mediaPontos: number;
  quantidadeMembros: number;
  dataCriacao: Date;
  dataAlteracao: Date;
  dataFim: Date;
  fechado: boolean;
  pago: boolean;
  valor: number;

  constructor(data: Partial<Grupo>) {
    Object.assign(this, data);
  }
}
