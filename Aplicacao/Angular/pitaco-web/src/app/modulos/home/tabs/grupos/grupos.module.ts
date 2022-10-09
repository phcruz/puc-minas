import { MaterialModule } from './../../../../util/material.module';
import { GruposRoutingModule } from './grupos-routing.module';
import { GruposComponent } from './grupos.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    GruposComponent,
  ],
  imports: [
    CommonModule,
    GruposRoutingModule,
    MaterialModule,
  ]
})
export class GruposModule { }
