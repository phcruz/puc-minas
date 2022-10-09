export class Liga {

  idLiga: number;
  nomeLiga: string;
  paisLiga: string;
  descricao: string;
  temporada: string;
  urlImagem: string;
  paga: boolean;
  valor: number;
  dataInicio: Date;
  dataFim: Date;

  constructor(data: Partial<Liga>) {
    Object.assign(this, data);
  }
}
