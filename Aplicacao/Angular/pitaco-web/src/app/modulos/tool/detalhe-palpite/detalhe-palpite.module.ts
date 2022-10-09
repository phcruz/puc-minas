import { DetalhePalpiteComponent } from './detalhe-palpite.component';
import { DetalhePalpiteRoutingModule } from './detalhe-palpite-routing.module';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    DetalhePalpiteComponent,
  ],
  imports: [
    CommonModule,
    DetalhePalpiteRoutingModule,
    MaterialModule,
  ]
})
export class DetalhePalpiteModule { }
