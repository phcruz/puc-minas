import { Palpite } from './palpite.model';
import { Equipe } from './equipe.model';

export class Partida {
  idPartida: number;
  idLiga: number;
  placarEquipeCasa: number;
  placarEquipeVisitante: number;
  fase: string;
  tagGrupo: string;
  localJogo: string;
  placarExtendidoEquipeCasa: string;
  placarExtendidoEquipeVisitante: string;
  dataHoraJogo: Date;
  dataHoraJogoTexto: string;
  permiteAposta: boolean;
  mostraPontuacao: boolean;
  // adicionais
  palpite: Palpite;
  mandante: Equipe;
  visitante: Equipe;
  urlImagemLogoLiga: string;

  tempoPartida: string;
  statusPartida: string;
  colorCard: string;
  golsEquipeCasa: string;
  golsEquipeVisitante: string;

  constructor(data: Partial<Partida>) {
    Object.assign(this, data);
  }
}
