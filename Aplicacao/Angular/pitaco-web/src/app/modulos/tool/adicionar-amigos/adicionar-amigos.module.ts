import { AdicionarAmigosRoutingModule } from './adicionar-amigos-routing.module';
import { MaterialModule } from './../../../util/material.module';
import { AdicionarAmigosComponent } from './adicionar-amigos.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AdicionarAmigosComponent,
  ],
  imports: [
    CommonModule,
    AdicionarAmigosRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class AdicionarAmigosModule { }
