import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ProdutosRoutingModule } from './produtos-routing.module';
import { ProdutosFormComponent } from './produtos-form/produtos-form.component';
import { DiretivasModule } from 'src/app/compartilhados/diretivas/diretivas.module';

@NgModule({
  declarations: [ProdutosFormComponent],
  imports: [
    CommonModule,
    ProdutosRoutingModule,
    ReactiveFormsModule,
    DiretivasModule,
  ],
})
export class ProdutosModule {}
