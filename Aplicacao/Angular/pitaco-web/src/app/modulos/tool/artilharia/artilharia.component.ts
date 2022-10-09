import { Component, OnInit } from '@angular/core';
import { AppService } from 'src/app/util/services/app.service';
import { ToastService } from 'src/app/util/services/toast.service';
import { DialogMensagemErroService } from 'src/app/util/services/dialog-mensagem-erro.service';
import { Constants } from 'src/app/util/constants';
import { UtilMetodos } from 'src/app/util/util-metodos';

@Component({
  selector: 'app-artilharia',
  templateUrl: './artilharia.component.html',
  styleUrls: ['./artilharia.component.css']
})
export class ArtilhariaComponent implements OnInit {

  artilheiros = [];
  idLiga: any;

  constructor(private appService: AppService,
              private dialogErroService: DialogMensagemErroService,
              private toastService: ToastService) { }

  ngOnInit() {
    this.idLiga = UtilMetodos.getItemSessionStorage(Constants.ID_LIGA);
    this.getListarArtilhariaLiga();
  }

  getListarArtilhariaLiga() {
    if (this.idLiga === null || this.idLiga === undefined) {
      // mostrar mensage de erro
    }
    this.appService.getArtilhariaLiga(this.idLiga).subscribe(
      response => {
        const lista = response.body;

        lista.forEach(element => {
          this.artilheiros.push({
            nome: element.nome,
            posicao: element.posicao,
            gols: element.gols,
            ranking: element.ranking,
            time: element.time,
            urlJogador: element.urlJogador,
            urlEscudoTime: element.urlEscudoTime
          });
        });
      },
      err => {
        this.dialogErroService.showMensagemErroDialog(err);
      }
    );
  }
}
