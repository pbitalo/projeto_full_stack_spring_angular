import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientesFormComponent } from './clientes-form/clientes-form.component';

const routes: Routes = [
  { path: '', component: ClientesFormComponent },
  { path: 'novo', component: ClientesFormComponent },
  { path: ':id/editar', component: ClientesFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClientesRoutingModule {}
