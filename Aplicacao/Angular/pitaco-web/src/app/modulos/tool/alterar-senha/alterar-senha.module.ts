import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AlterarSenhaRoutingModule } from './alterar-senha-routing.module';
import { AlterarSenhaComponent } from './alterar-senha.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    AlterarSenhaComponent,
  ],
  imports: [
    CommonModule,
    AlterarSenhaRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class AlterarSenhaModule { }
