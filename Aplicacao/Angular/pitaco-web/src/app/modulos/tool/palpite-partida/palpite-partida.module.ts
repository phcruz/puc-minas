import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PalpitePartidaRoutingModule } from './palpite-partida-routing.module';
import { PalpitePartidaComponent } from './palpite-partida.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    PalpitePartidaComponent,
  ],
  imports: [
    CommonModule,
    PalpitePartidaRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    NgxChartsModule,
  ]
})
export class PalpitePartidaModule { }
