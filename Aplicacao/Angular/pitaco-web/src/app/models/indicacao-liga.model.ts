export class IndicacaoLiga {

  idIndicacaoLiga: number;
  idUsuario: number;
  nomeLiga: string;
  paisLiga: string;
  observacao: string;
  dataIndicacao: Date;
  ativa: boolean;

  constructor(data: Partial<IndicacaoLiga>) {
    Object.assign(this, data);
  }
}
