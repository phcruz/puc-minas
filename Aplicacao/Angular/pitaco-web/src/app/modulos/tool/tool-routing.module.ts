import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppToolComponent } from './app-tool/app-tool.component';
import { AuthGuard } from 'src/app/util/guard/auth-guard';

const routes: Routes = [
  {
    path:  '', component:  AppToolComponent,
    children: [
      {
        path:  'tool/partidas-liga',
        loadChildren: () => import('./partidas-liga/partidas-liga.module').then(m => m.PartidasLigaModule),
        canActivate: [AuthGuard] },
      {
        path:  'tool/classificacao',
        loadChildren: () => import('./classificacao/classificacao.module').then(m => m.ClassificacaoModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/pontuadores',
        loadChildren: () => import('./pontuadores/pontuadores.module').then(m => m.PontuadoresModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/detalhe-usuario',
        loadChildren: () => import('./detalhe-usuario/detalhe-usuario.module').then(m => m.DetalheUsuarioModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/perfil-usuario',
        loadChildren: () => import('./perfil-usuario/perfil-usuario.module').then(m => m.PerfilUsuarioModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/alterar-avatar',
        loadChildren: () => import('./alterar-avatar/alterar-avatar.module').then(m => m.AlterarAvatarModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/mais-ligas',
        loadChildren: () => import('./mais-ligas/mais-ligas.module').then(m => m.MaisLigasModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/mais-grupos',
        loadChildren: () => import('./mais-grupos/mais-grupos.module').then(m => m.MaisGruposModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/detalhe-grupo',
        loadChildren: () => import('./detalhe-grupo/detalhe-grupo.module').then(m => m.DetalheGrupoModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/indicar-liga',
        loadChildren: () => import('./indicar-liga/indicar-liga.module').then(m => m.IndicarLigaModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/atualizar-cadastro',
        loadChildren: () => import('./atualizar-cadastro/atualizar-cadastro.module').then(m => m.AtualizarCadastroModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/alterar-senha',
        loadChildren: () => import('./alterar-senha/alterar-senha.module').then(m => m.AlterarSenhaModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/criar-grupo',
        loadChildren: () => import('./criar-grupo/criar-grupo.module').then(m => m.CriarGrupoModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/editar-grupo',
        loadChildren: () => import('./editar-grupo/editar-grupo.module').then(m => m.EditarGrupoSenhaModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/adicionar-amigos',
        loadChildren: () => import('./adicionar-amigos/adicionar-amigos.module').then(m => m.AdicionarAmigosModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/convidar-amigos',
        loadChildren: () => import('./convidar-amigos/convidar-amigos.module').then(m => m.ConvidarAmigosModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/gerenciar-grupo',
        loadChildren: () => import('./gerenciar-grupo/gerenciar-grupo.module').then(m => m.GerenciarGrupoModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/palpite-partida',
        loadChildren: () => import('./palpite-partida/palpite-partida.module').then(m => m.PalpitePartidaModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/historico-partida',
        loadChildren: () => import('./historico-partida/historico-partida.module').then(m => m.HistoricoPartidaModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/detalhe-palpite',
        loadChildren: () => import('./detalhe-palpite/detalhe-palpite.module').then(m => m.DetalhePalpiteModule),
        canActivate: [AuthGuard]
      },
      {
        path:  'tool/usuario-palpite',
        loadChildren: () => import('./usuario-palpite/usuario-palpite.module').then(m => m.UsuarioPalpiteModule),
        canActivate: [AuthGuard] },
      {
        path:  'tool/artilharia',
        loadChildren: () => import('./artilharia/artilharia.module').then(m => m.ArtilhariaModule),
        canActivate: [AuthGuard]
      },
      {
        path : '**',
        loadChildren: () => import('../../page-not-found/page-not-found.module').then(m => m.PageNotFoundModule)
      },
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ToolRoutingModule { }
