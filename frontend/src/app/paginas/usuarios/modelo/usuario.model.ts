import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';
import { RetornoPadrao } from 'src/app/compartilhados/modelos/retorno-padrao.model';

export interface UsuarioModelo extends RecursoPadraoModelo {
  data: RetornoPadrao<UsuarioModelo>;
  name?: string;
  email?: string;
  password?: string;
}
