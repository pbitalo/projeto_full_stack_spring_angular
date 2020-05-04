import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VendasFormComponent } from './vendas-form/vendas-form.component';

const routes: Routes = [
  { path: '', component: VendasFormComponent },
  { path: 'novo', component: VendasFormComponent },
  { path: ':id/editar', component: VendasFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VendasRoutingModule {}
