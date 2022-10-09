import { ClassificacaoRoutingModule } from './classificacao-routing.module';
import { ClassificacaoComponent } from './classificacao.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    ClassificacaoComponent,
  ],
  imports: [
    CommonModule,
    ClassificacaoRoutingModule,
    MaterialModule,
  ]
})
export class ClassificacaoModule { }
