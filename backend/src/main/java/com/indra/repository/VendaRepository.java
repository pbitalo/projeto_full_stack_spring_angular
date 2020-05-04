package com.indra.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indra.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
	
	@Query(value = "select DISTINCT BANDEIRA from Venda", nativeQuery = true)
	List<String> findByBandeira();		

	@Query(value = "SELECT \r\n" + 
			"CLIENTE.NOME AS NOME_CLIENTE , CLIENTE.INSTALACAO_CODIGO , CLIENTE.MUNICIPIO ,CLIENTE.SIGLA_ESTADO , CLIENTE.SIGLA_REGIAO , VENDA.BANDEIRA ,  VENDA.DATA_COLETA , VENDA.VALOR_COMPRA , VENDA.VALOR_VENDA , PRODUTO.NOME AS NOME_PRODUTO , PRODUTO.UNIDADE_MEDIDA\r\n" + 
			"FROM CLIENTE\r\n" + 
			"JOIN VENDA\r\n" + 
			" ON CLIENTE.ID=VENDA.CLIENTE_ID \r\n" + 
			"JOIN PRODUTO\r\n" + 
			" ON VENDA.PRODUTO_ID=PRODUTO.ID\r\n" + 
			"WHERE VENDA.BANDEIRA LIKE %:nomeBandeira%", nativeQuery = true)	
	List<Object> findAllPorBandeira(@Param("nomeBandeira") String nomeBandeira);	

	@Query(value = "SELECT CLIENTE.NOME AS NOME_CLIENTE , CLIENTE.INSTALACAO_CODIGO , CLIENTE.MUNICIPIO ,CLIENTE.SIGLA_ESTADO , CLIENTE.SIGLA_REGIAO , VENDA.BANDEIRA ,  VENDA.DATA_COLETA , VENDA.VALOR_COMPRA , VENDA.VALOR_VENDA , PRODUTO.NOME AS NOME_PRODUTO , PRODUTO.UNIDADE_MEDIDA FROM CLIENTE JOIN VENDA ON CLIENTE.ID=VENDA.CLIENTE_ID JOIN PRODUTO ON VENDA.PRODUTO_ID=PRODUTO.ID ORDER BY VENDA.DATA_COLETA DESC", nativeQuery = true)
	List<Object> findAllPorDataColeta(@Param("tipo") String tipo);
	
	Page<Venda> findAll(Pageable pageable);
	
}
