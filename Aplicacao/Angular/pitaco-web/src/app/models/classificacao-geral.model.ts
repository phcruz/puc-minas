export class ClassificacaoGeral {

  posicao: number;
  idEquipe: number;
  urlImagemEquipe: string;
  nomeEquipe: string;
  quantidadePontos: number;
  quantidadeJogos: number;
  quantidadeVitorias: number;
  quantidadeEmpates: number;
  quantidadeDerrotas: number;
  saldoGols: number;

  constructor(data: Partial<ClassificacaoGeral>) {
    Object.assign(this, data);
  }
}
