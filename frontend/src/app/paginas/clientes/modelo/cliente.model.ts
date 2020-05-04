import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';
import { RetornoPadrao } from 'src/app/compartilhados/modelos/retorno-padrao.model';

export interface ClienteModelo extends RecursoPadraoModelo {
  data: RetornoPadrao<ClienteModelo>;
  nome?: string;
  instalacaoCodigo?: number;
  siglaRegiao?: string;
  siglaEstado?: string;
  municipio?: string;
}
