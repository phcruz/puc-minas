import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndicarLigaComponent } from './indicar-liga.component';

const routes: Routes = [
  {
    path:  '', component:  IndicarLigaComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IndicarLigaRoutingModule { }
