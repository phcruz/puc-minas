export class Ranking {
  posicao: number;
  pontos: number;
  idUsuario: number;
  nomeUsuario: string;
  idLiga: number;
  nomeLiga: string;
  urlImagem: string;
  // adicionados para exibir criterios de desempate
  acertoCincoPontos: number;
  acertoTresPontos: number;
  acertoUmPonto: number;
  acertoPontuou: number;

  constructor(data: Partial<Ranking>) {
    Object.assign(this, data);
  }
}
