export class PontuadorRodada {

  idUsuario: number;
  nome: string;
  urlImagem: string;
  pontos: number;

  constructor(data: Partial<PontuadorRodada>) {
    Object.assign(this, data);
  }
}
