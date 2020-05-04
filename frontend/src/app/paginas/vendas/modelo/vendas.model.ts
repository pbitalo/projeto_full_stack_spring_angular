import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';
import { RetornoPadrao } from 'src/app/compartilhados/modelos/retorno-padrao.model';

export interface VendasModelo extends RecursoPadraoModelo {
  data: RetornoPadrao<VendasModelo>;
  bandeira?: string;
  dataColeta?: string;
  valorCompra?: string;
  valorVenda?: string;
  clienteId?: number;
  produtoId?: number;
}
