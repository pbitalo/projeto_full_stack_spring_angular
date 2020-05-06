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

	@Query(value = "SELECT c.NOME AS NOME_CLIENTE , c.INSTALACAO_CODIGO , c.MUNICIPIO , c.SIGLA_ESTADO , c.SIGLA_REGIAO , v.BANDEIRA ,  v.DATA_COLETA , v.VALOR_COMPRA , v.VALOR_VENDA , p.NOME AS NOME_PRODUTO , p.UNIDADE_MEDIDA FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id WHERE c.SIGLA_REGIAO LIKE %:siglaRegiao%", 
		   countQuery = "SELECT COUNT(*) FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id WHERE c.SIGLA_REGIAO LIKE %:siglaRegiao%", 
		   nativeQuery = true)	
	Page<Object> findDataPorRegioes(Pageable pageable, @Param("siglaRegiao") String siglaRegiao);		
	
}
