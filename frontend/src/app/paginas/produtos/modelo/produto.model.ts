import { RecursoPadraoModelo } from 'src/app/compartilhados/modelos/recurso-padrao.model';

export interface ProdutoModelo extends RecursoPadraoModelo {
  nome?: string;
  unidadeMedida?: string;
  preco?: string;
}
