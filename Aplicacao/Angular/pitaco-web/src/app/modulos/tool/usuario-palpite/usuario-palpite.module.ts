import { UsuarioPalpiteRoutingModule } from './usuario-palpite-routing.module';
import { UsuarioPalpiteComponent } from './usuario-palpite.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    UsuarioPalpiteComponent,
  ],
  imports: [
    CommonModule,
    UsuarioPalpiteRoutingModule,
    MaterialModule,
  ]
})
export class UsuarioPalpiteModule { }
