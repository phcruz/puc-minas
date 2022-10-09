import { ConvidarAmigosRoutingModule } from './convidar-amigos-routing.module';
import { ConvidarAmigosComponent } from './convidar-amigos.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    ConvidarAmigosComponent,
  ],
  imports: [
    CommonModule,
    ConvidarAmigosRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class ConvidarAmigosModule { }
