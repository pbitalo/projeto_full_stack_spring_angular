import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { UsuariosRoutingModule } from './usuarios-routing.module';
import { UsuariosFormComponent } from './usuarios-form/usuarios-form.component';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [UsuariosFormComponent],
  imports: [
    CommonModule,
    UsuariosRoutingModule,
    ReactiveFormsModule,
    NgxPaginationModule,
  ],
})
export class UsuariosModule {}
