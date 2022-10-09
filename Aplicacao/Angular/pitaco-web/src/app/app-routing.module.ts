import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppHomeComponent } from './modulos/home/app-home/app-home.component';
import { AppToolComponent } from './modulos/tool/app-tool/app-tool.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
  },
  {
    path : 'cadastro',
    loadChildren: () => import('./cadastro/cadastro.module').then(m => m.CadastroModule),
  },
  { path : 'app', component: AppHomeComponent },
  { path : 'tool', component: AppToolComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
