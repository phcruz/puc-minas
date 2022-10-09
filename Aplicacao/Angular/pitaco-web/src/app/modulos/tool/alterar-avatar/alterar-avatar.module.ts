import { AlterarAvatarRoutingModule } from './alterar-avatar-routing.module';
import { AlterarAvatarComponent } from './alterar-avatar.component';
import { MaterialModule } from './../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    AlterarAvatarComponent,
  ],
  imports: [
    CommonModule,
    AlterarAvatarRoutingModule,
    MaterialModule,
  ]
})
export class AlterarAvatarModule { }
