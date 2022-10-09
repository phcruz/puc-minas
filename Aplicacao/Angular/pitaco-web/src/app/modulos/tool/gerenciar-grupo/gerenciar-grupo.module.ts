import { GerenciarGrupoComponent } from './gerenciar-grupo.component';
import { GerenciarGrupoRoutingModule } from './gerenciar-grupo-routing.module';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    GerenciarGrupoComponent,
  ],
  imports: [
    CommonModule,
    GerenciarGrupoRoutingModule,
    MaterialModule,
  ]
})
export class GerenciarGrupoModule { }
