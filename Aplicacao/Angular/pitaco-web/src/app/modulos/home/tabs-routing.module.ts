import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppHomeComponent } from './app-home/app-home.component';
import { HomeComponent } from './tabs/home/home.component';
import { AuthGuard } from 'src/app/util/guard/auth-guard';

const routes: Routes = [
  {
    path:  '', component:  AppHomeComponent,
    children: [
      {
        path:  'app/home',
        loadChildren: () => import('./tabs/home/home.module').then(m => m.HomeModule),
        canActivate: [AuthGuard]},
      {
        path:  'app/ligas',
        loadChildren: () => import('./tabs/ligas/ligas.module').then(m => m.LigasModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'app/grupos',
        loadChildren: () => import('./tabs/grupos/grupos.module').then(m => m.GruposModule),
        canActivate: [AuthGuard]},
      {
        path:  'app/ranking',
        loadChildren: () => import('./tabs/ranking/ranking.module').then(m => m.RankingModule),
        canActivate: [AuthGuard]
      },
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TabsRoutingModule { }
