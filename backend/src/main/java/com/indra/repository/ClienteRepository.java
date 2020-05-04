package com.indra.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indra.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Page<Cliente> findAll(Pageable pageable);
	
	Optional<Cliente> findByNomeEquals(String nome);
	
	@Query(value = "select DISTINCT MUNICIPIO from Cliente", nativeQuery = true)
	List<String> findByMunicipos();	
	
	@Query(value = "select DISTINCT SIGLA_REGIAO from Cliente", nativeQuery = true)
	List<String> findByRegioes();		
	
	@Query(value = "SELECT ( SUM(VALOR_VENDA) / COUNT(*) ) AS media FROM cliente JOIN venda ON cliente.id=venda.cliente_id WHERE cliente.municipio LIKE %:nomeMunicipio%", nativeQuery = true)
	BigDecimal findMediaPrecoCombustivel(@Param("nomeMunicipio") String nomeMunicipio);	
	
	@Query(value = "SELECT ( SUM(VALOR_COMPRA) / COUNT(*) ) AS media FROM cliente JOIN venda ON cliente.id=venda.cliente_id WHERE cliente.municipio LIKE %:nomeMunicipio%", nativeQuery = true)
	BigDecimal findMediaCompraCombustivel(@Param("nomeMunicipio") String nomeMunicipio);		
	
	@Query(value = "SELECT ( SUM(VALOR_VENDA) / COUNT(*) ) AS media FROM cliente JOIN venda ON cliente.id=venda.cliente_id WHERE venda.bandeira LIKE %:nomeBandeira%", nativeQuery = true)
	BigDecimal findMediaPrecoCombustivelPorBandeira(@Param("nomeBandeira") String nomeBandeira);	
	
	@Query(value = "SELECT ( SUM(VALOR_COMPRA) / COUNT(*) ) AS media FROM cliente JOIN venda ON cliente.id=venda.cliente_id WHERE venda.bandeira LIKE %:nomeBandeira%", nativeQuery = true)
	BigDecimal findMediaCompraCombustivelPorBandeira(@Param("nomeBandeira") String nomeBandeira);	
	
	@Query(value = "SELECT \r\n" + 
			"CLIENTE.NOME AS NOME_CLIENTE , CLIENTE.INSTALACAO_CODIGO , CLIENTE.MUNICIPIO ,CLIENTE.SIGLA_ESTADO , CLIENTE.SIGLA_REGIAO , VENDA.BANDEIRA ,  VENDA.DATA_COLETA , VENDA.VALOR_COMPRA , VENDA.VALOR_VENDA , PRODUTO.NOME AS NOME_PRODUTO , PRODUTO.UNIDADE_MEDIDA\r\n" + 
			"FROM CLIENTE\r\n" + 
			"JOIN VENDA\r\n" + 
			" ON CLIENTE.ID=VENDA.CLIENTE_ID \r\n" + 
			"JOIN PRODUTO\r\n" + 
			" ON VENDA.PRODUTO_ID=PRODUTO.ID\r\n" + 
			"WHERE CLIENTE.SIGLA_REGIAO LIKE %:siglaRegiao%", nativeQuery = true)
	List<Object> findDataPorRegioes(@Param("siglaRegiao") String siglaRegiao);	
	
}
