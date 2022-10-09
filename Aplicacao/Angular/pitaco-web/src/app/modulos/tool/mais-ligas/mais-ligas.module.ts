import { MaisLigasRoutingModule } from './mais-ligas-routing.module';
import { MaisLigasComponent } from './mais-ligas.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    MaisLigasComponent,
  ],
  imports: [
    CommonModule,
    MaisLigasRoutingModule,
    MaterialModule,
  ]
})
export class MaisLigasModule { }
