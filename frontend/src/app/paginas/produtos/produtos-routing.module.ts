import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProdutosFormComponent } from './produtos-form/produtos-form.component';

const routes: Routes = [
  { path: '', component: ProdutosFormComponent },
  { path: 'novo', component: ProdutosFormComponent },
  { path: ':id/editar', component: ProdutosFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProdutosRoutingModule {}
