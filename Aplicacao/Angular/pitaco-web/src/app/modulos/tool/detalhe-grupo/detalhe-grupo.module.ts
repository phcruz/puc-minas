import { DetalheGrupoRoutingModule } from './detalhe-grupo-routing.module';
import { DetalheGrupoComponent } from './detalhe-grupo.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    DetalheGrupoComponent,
  ],
  imports: [
    CommonModule,
    DetalheGrupoRoutingModule,
    MaterialModule,
  ]
})
export class DetalheGrupoModule { }
