import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AtualizarCadastroRoutingModule } from './atualizar-cadastro-routing.module';
import { AtualizarCadastroComponent } from './atualizar-cadastro.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    AtualizarCadastroComponent,
  ],
  imports: [
    CommonModule,
    AtualizarCadastroRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class AtualizarCadastroModule { }
