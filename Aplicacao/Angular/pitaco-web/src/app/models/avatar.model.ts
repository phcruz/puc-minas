export class Avatar {

  idAvatar: number;
  descricao: string;
  urlImagem: string;
  dataCadastro: Date;
  ativo: boolean;

  constructor(data: Partial<Avatar>) {
    Object.assign(this, data);
  }
}
