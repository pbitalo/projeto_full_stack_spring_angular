import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HistoricoRoutingModule } from './historico-routing.module';
import { HistoricoFormComponent } from './historico-form/historico-form.component';
import { ModulosCompartilhados } from 'src/app/compartilhados/modulos/modulos-compartilhados.module';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [HistoricoFormComponent],
  imports: [
    CommonModule,
    HistoricoRoutingModule,
    ReactiveFormsModule,
    ModulosCompartilhados,
    NgxPaginationModule,
  ],
})
export class HistoricoModule {}
