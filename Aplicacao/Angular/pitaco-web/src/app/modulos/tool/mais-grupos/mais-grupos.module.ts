import { MaisGruposRoutingModule } from './mais-grupos-routing.module';
import { MaisGruposComponent } from './mais-grupos.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    MaisGruposComponent,
  ],
  imports: [
    CommonModule,
    MaisGruposRoutingModule,
    MaterialModule,
  ]
})
export class MaisGruposModule { }
