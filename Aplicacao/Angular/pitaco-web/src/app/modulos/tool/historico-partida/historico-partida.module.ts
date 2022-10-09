import { HistoricoPartidaRoutingModule } from './historico-partida-routing.module';
import { HistoricoPartidaComponent } from './historico-partida.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    HistoricoPartidaComponent,
  ],
  imports: [
    CommonModule,
    HistoricoPartidaRoutingModule,
    MaterialModule,
  ]
})
export class HistoricoPartidaModule { }
