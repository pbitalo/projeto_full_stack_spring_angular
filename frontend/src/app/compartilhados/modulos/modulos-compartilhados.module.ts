import { INgxSelectOptions, NgxSelectModule } from 'ngx-select-ex';
import { NgModule } from '@angular/core';

const padraoSelectOptions: INgxSelectOptions = {
  optionValueField: 'id',
  optionTextField: 'nome',
  keepSelectedItems: false,
};

@NgModule({
  imports: [NgxSelectModule.forRoot(padraoSelectOptions)],
  exports: [NgxSelectModule],
})
export class ModulosCompartilhados {}
