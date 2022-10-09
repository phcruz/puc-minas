import { EstatisticaPlacar } from './estatistica-placar.model';

export class EstatisticaPartida {

  vitoriaCasa: number;
  vitoriaVisitante: number;
  empate: number;
  totalPalpites: number;

  listaEstatisticaPlacar: EstatisticaPlacar[];

  constructor(data: Partial<EstatisticaPartida>) {
    Object.assign(this, data);
  }
}
