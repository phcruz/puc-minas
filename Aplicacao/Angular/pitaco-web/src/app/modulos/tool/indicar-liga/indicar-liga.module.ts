import { IndicarLigaRoutingModule } from './indicar-liga-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndicarLigaComponent } from './indicar-liga.component';


@NgModule({
  declarations: [
    IndicarLigaComponent,
  ],
  imports: [
    CommonModule,
    IndicarLigaRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class IndicarLigaModule { }
