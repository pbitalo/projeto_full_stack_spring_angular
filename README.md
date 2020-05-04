Considerações sobre os projetos

----------------------

1- Tecnologias utilizadas:

Front-end
Angular 9, webpack, typescript3.8, bootstrap4, primeicons, primeng etc 
=>npm install 
=>ng s

Back-end
Spring 2.2.6.RELEASE, java 1.8, h2database, projectlombok, jpa, etc.

----------------------

2- Sobre a carga da base de dados e objetivos ao desenvolver os projetos.

Fiz uma cópia da base de dados e coloquei dentro do projeto backend. A arquivo é muito grande, por isso dividi o mesma com dados diversos para ter uma 
base relativamente distribuída e menor, isso se fez necessário por causa da importação que ficou demorada, pois quando o usuário requisita essa funcionalidade, 
o sistema lê linha a linha, fazendo verificações se o produto já está cadastrado, se não tiver, cadastra, assim como realiza esse processo para as demais entidades
do sistema (inclusive não fiz todas as entidades que seriam necessárias para um sistema que "rodaria" em um cliente, por exemplo, fiz apenas o básico para
concluir todas as funcionalidades). As entidades criadas foram Usuário, Cliente, Produto, Venda e HistoricoPreco, no entanto, seria necesário criar para 
siglaEstado e outros, mas a não criação não foi um impeditivo para a conclusão, embora não seja o ideal. Como o tempo foi relativamente curto para fazer o que 
eu considerava ideal, como realizar a carga dos dados de forma distribuída entre outras features que poderiam agregar ao desafio, tive a preocupação em fazer um
protótipo funcional que contemple apenas as funcionalidades pedidas, porém, como falei anteriormente, não tive preocupação/tempo em tornar todos os fluxos ideais 
ao meu ver.

----------------------

3- Mapeamento dos requisitos e suas respectivas implementações.

----------------------

Credenciais para acesso ao sistema. (após criar novos usuários, é só utilizar os novos cadastrados se assim quiser..)

usuario: italo
senha: 123456

OBs: A autenticação é meramente ilustrativa.*

----------------------

*****Implemente uma documentação interativa. O acesso a essa URI não requer autenticação

Swagger
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

Acesso ao banco H2

http://localhost:8080/h2-console/
JDBC URL: jdbc:h2:mem:indradb
Driver Class: org.h2.Driver
login: sa
senha: sa

----------------------

*****Implementar recurso para CRUD de usuários

Acesso atraves do menu superior Usuários -> CRUD

----------------------

CRUD Clientes
Acesso atraves do menu superior Clientes -> CRUD

----------------------

CRUD Produtos
Acesso atraves do menu superior Produtos -> CRUD

----------------------

CRUD Vendas
Acesso atraves do menu superior Vendas -> CRUD


----------------------

*****Implementar recurso para CRUD de histórico de preço de combustível

Acesso atraves do menu superior Vendas -> HISTORICO
O histórico é gerado quando uma nova venda é registrada ou editada.

----------------------

*****Implementar recurso para importação de csv

Para importar a base, acesse menu -> Relatórios / Imp. Base -> Diversos -> Clicar no botão (Importar Base) 
[Aguarde o loading desaparecer]
Após a carga dos dados, os campos select's serão carregados auto. (Municípios, Bandeira / Distribuidora , Regiões)

----------------------

*****Implementar recurso que retorne a média de preço de combustível com base no nome do município
*****Implementar recurso que retorne o valor médio do valor da compra e do valor da venda por município
acesse menu -> Relatórios / Imp. Base -> Diversos , após ter carregado a base, selecione um municipio o as médias serão carregadas para os campos a direita.

----------------------

*****Implementar recurso que retorne todas as informações importadas por sigla da região
acesse menu -> Relatórios / Imp. Base -> Diversos , após ter carregado a base, selecione uma região no combo.

----------------------

*****Implementar recurso que retorne os dados agrupados por distribuidora
acesse menu -> Relatórios / Imp. Base -> Diversos , após ter carregado a base, selecione uma Bandeira / Distribuidora e clique no botão 
(Pegar dados agrupados por Distribuídora)

----------------------

*****Implementar recurso que retorne os dados agrupados pela data da coleta
acesse menu -> Relatórios / Imp. Base -> Diversos , após ter carregado a base, clique no botão 
(Pegar dados agrupados por Data), a ordem que virá será descendente

----------------------

*****Implementar recurso que retorne o valor médio do valor da compra e do valor da venda por bandeira
acesse menu -> Relatórios / Imp. Base -> Diversos , após ter carregado a base, selecione uma Bandeira / Distribuidora e os valores serãm exibidos 
nos campos a direita.

----------------------