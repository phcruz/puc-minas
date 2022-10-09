import { CriarGrupoRoutingModule } from './criar-grupo-routing.module';
import { CriarGrupoComponent } from './criar-grupo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    CriarGrupoComponent,
  ],
  imports: [
    CommonModule,
    CriarGrupoRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class CriarGrupoModule { }
