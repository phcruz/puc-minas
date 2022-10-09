export class Equipe {

  idEquipe: number;
  nome: string;
  siglaEquipe: string;
  nomeCompleto: string;
  nomeEquipeApi: string;
  urlImagemEquipe: string;
  dataCadastro: Date;

  constructor(data: Partial<Equipe>) {
    Object.assign(this, data);
  }
}
