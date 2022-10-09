export class EstatisticaPlacar {

  placarPalpite: string;
  totalPlacar: number;

  constructor(data: Partial<EstatisticaPlacar>) {
    Object.assign(this, data);
  }
}
