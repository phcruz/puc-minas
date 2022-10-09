export class Usuario {

  idUsuario: number;
  nome: string;
  email: string;
  dataNascimento: Date;
  telefone: string;
  urlImagem: string;
  ativo: boolean;
  dataCadastro: Date;
  timeCoracao: string;

  constructor(data: Partial<Usuario>) {
    Object.assign(this, data);
  }
}
