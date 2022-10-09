import { DetalheUsuarioRoutingModule } from './detalhe-usuario-routing.module';
import { DetalheUsuarioComponent } from './detalhe-usuario.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    DetalheUsuarioComponent,
  ],
  imports: [
    CommonModule,
    DetalheUsuarioRoutingModule,
    MaterialModule,
  ]
})
export class DetalheUsuarioModule { }
