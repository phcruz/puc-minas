import { PerfilUsuarioRoutingModule } from './perfil-usuario-routing.module';
import { PerfilUsuarioComponent } from './perfil-usuario.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    PerfilUsuarioComponent,
  ],
  imports: [
    CommonModule,
    PerfilUsuarioRoutingModule,
    MaterialModule,
  ]
})
export class PerfilUsuarioModule { }
