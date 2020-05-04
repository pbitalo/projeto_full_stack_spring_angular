import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HistoricoFormComponent } from './historico-form/historico-form.component';

const routes: Routes = [
  { path: '', component: HistoricoFormComponent },
  { path: 'novo', component: HistoricoFormComponent },
  { path: ':id/editar', component: HistoricoFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HistoricoRoutingModule {}
