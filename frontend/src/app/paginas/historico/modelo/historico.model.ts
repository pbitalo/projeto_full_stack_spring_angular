import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';
import { RetornoPadrao } from 'src/app/compartilhados/modelos/retorno-padrao.model';

export interface HistoricoModelo extends RecursoPadraoModelo {
  data: RetornoPadrao<HistoricoModelo>;
  dataColeta?: string;
  valorVenda?: string;
  produtoId?: string;
}
