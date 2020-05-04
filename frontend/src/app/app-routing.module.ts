import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'login',
    loadChildren: () =>
      import('./paginas/login/login.module').then((m) => m.LoginModule),
  },
  {
    path: 'usuarios',
    loadChildren: () =>
      import('./paginas/usuarios/usuarios.module').then(
        (m) => m.UsuariosModule
      ),
  },
  {
    path: 'clientes',
    loadChildren: () =>
      import('./paginas/clientes/clientes.module').then(
        (m) => m.ClientesModule
      ),
  },
  {
    path: 'produtos',
    loadChildren: () =>
      import('./paginas/produtos/produtos.module').then(
        (m) => m.ProdutosModule
      ),
  },
  {
    path: 'vendas',
    loadChildren: () =>
      import('./paginas/vendas/vendas.module').then((m) => m.VendasModule),
  },
  {
    path: 'historico',
    loadChildren: () =>
      import('./paginas/historico/historico.module').then(
        (m) => m.HistoricoModule
      ),
  },
  {
    path: 'relatorios',
    loadChildren: () =>
      import('./paginas/relatorios/relatorios.module').then(
        (m) => m.RelatoriosModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
