import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { RelatoriosRoutingModule } from './relatorios-routing.module';
import { RelatoriosFormComponent } from './relatorios-form/relatorios-form.component';
import { ModulosCompartilhados } from 'src/app/compartilhados/modulos/modulos-compartilhados.module';
import { ProgressBarModule } from 'primeng/progressbar';

@NgModule({
  declarations: [RelatoriosFormComponent],
  imports: [
    CommonModule,
    RelatoriosRoutingModule,
    ReactiveFormsModule,
    ModulosCompartilhados,
    ProgressBarModule,
  ],
})
export class RelatoriosModule {}
