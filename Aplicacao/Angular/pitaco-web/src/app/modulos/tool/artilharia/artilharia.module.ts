import { ArtilhariaRoutingModule } from './artilharia-routing.module';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArtilhariaComponent } from './artilharia.component';


@NgModule({
  declarations: [
    ArtilhariaComponent,
  ],
  imports: [
    CommonModule,
    ArtilhariaRoutingModule,
    MaterialModule,
  ]
})
export class ArtilhariaModule { }
