import { TabsRoutingModule } from './tabs-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from 'src/app/util/material.module';
import { AppRoutingModule } from 'src/app/app-routing.module';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    TabsRoutingModule,
    AppRoutingModule,
  ]
})
export class TabsModule { }
