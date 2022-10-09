import { LigasRoutingModule } from './ligas-routing.module';
import { LigasComponent } from './ligas.component';
import { MaterialModule } from './../../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    LigasComponent,
  ],
  imports: [
    CommonModule,
    LigasRoutingModule,
    MaterialModule,
  ]
})
export class LigasModule { }
