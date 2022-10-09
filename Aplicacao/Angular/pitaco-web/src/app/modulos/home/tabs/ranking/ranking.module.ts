import { RankingRoutingModule } from './ranking-routing.module';
import { RankingComponent } from './ranking.component';
import { MaterialModule } from './../../../../util/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    RankingComponent,
  ],
  imports: [
    CommonModule,
    RankingRoutingModule,
    MaterialModule,
  ]
})
export class RankingModule { }
