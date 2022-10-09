import { AdicionarAmigosComponent } from './adicionar-amigos.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path:  '', component:  AdicionarAmigosComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdicionarAmigosRoutingModule { }
