import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';

export interface UsuarioModelo extends RecursoPadraoModelo {
  name?: string;
  email?: string;
  password?: string;
}
