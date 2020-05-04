import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgMaskDirective } from './ngmask.directive';

@NgModule({
  declarations: [NgMaskDirective],
  imports: [CommonModule],
  exports: [NgMaskDirective],
})
export class DiretivasModule {}
