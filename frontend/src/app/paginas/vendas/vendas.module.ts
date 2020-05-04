import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { VendasRoutingModule } from './vendas-routing.module';
import { VendasFormComponent } from './vendas-form/vendas-form.component';
import { ModulosCompartilhados } from 'src/app/compartilhados/modulos/modulos-compartilhados.module';
import { DiretivasModule } from 'src/app/compartilhados/diretivas/diretivas.module';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [VendasFormComponent],
  imports: [
    CommonModule,
    VendasRoutingModule,
    ReactiveFormsModule,
    ModulosCompartilhados,
    DiretivasModule,
    NgxPaginationModule,
  ],
})
export class VendasModule {}
