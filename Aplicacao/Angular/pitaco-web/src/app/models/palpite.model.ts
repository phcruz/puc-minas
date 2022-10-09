export class Palpite {
  idPalpite: number;
  idPartida: number;
  idUsuario: number;
  idLiga: number;
  placarEquipeCasa: number;
  placarEquipeVisitante: number;
  pontos: number;
  dataCadastro: Date;
  dataAlteracao: Date;

  constructor(data: Partial<Palpite>) {
    Object.assign(this, data);
  }
}
