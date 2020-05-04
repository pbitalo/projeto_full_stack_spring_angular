import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RelatoriosFormComponent } from './relatorios-form/relatorios-form.component';

const routes: Routes = [
  { path: '', component: RelatoriosFormComponent },
  { path: 'novo', component: RelatoriosFormComponent },
  { path: ':id/editar', component: RelatoriosFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RelatoriosRoutingModule {}
