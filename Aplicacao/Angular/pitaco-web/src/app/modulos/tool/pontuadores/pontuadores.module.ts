import { PontuadoresRoutingModule } from './pontuadores-routing.module';
import { PontuadoresComponent } from './pontuadores.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    PontuadoresComponent,
  ],
  imports: [
    CommonModule,
    PontuadoresRoutingModule,
    MaterialModule,
  ]
})
export class PontuadoresModule { }
