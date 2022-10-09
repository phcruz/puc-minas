import { PartidasLigaRoutingModule } from './partidas-liga-routing.module';
import { PartidasLigaComponent } from './partidas-liga.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    PartidasLigaComponent,
  ],
  imports: [
    CommonModule,
    PartidasLigaRoutingModule,
    MaterialModule,
  ]
})
export class PartidasLigaModule { }
