import { EditarGrupoRoutingModule } from './editar-grupo-routing.module';
import { EditarGrupoComponent } from './editar-grupo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    EditarGrupoComponent,
  ],
  imports: [
    CommonModule,
    EditarGrupoRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class EditarGrupoSenhaModule { }
