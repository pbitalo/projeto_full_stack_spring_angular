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

	@Query(value = "SELECT c.NOME AS NOME_CLIENTE , c.INSTALACAO_CODIGO , c.MUNICIPIO , c.SIGLA_ESTADO , c.SIGLA_REGIAO , v.BANDEIRA ,  v.DATA_COLETA , v.VALOR_COMPRA , v.VALOR_VENDA , p.NOME AS NOME_PRODUTO , p.UNIDADE_MEDIDA FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id WHERE v.BANDEIRA LIKE %:nomeBandeira%", 
		   countQuery = "SELECT COUNT(*) FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id WHERE v.BANDEIRA LIKE %:nomeBandeira%", 
		   nativeQuery = true)	
	Page<Object> findAllPorBandeira(@Param("nomeBandeira") String nomeBandeira , Pageable pageable);	

	@Query(value = "SELECT c.NOME AS NOME_CLIENTE , c.INSTALACAO_CODIGO , c.MUNICIPIO , c.SIGLA_ESTADO , c.SIGLA_REGIAO , v.BANDEIRA ,  v.DATA_COLETA , v.VALOR_COMPRA , v.VALOR_VENDA , p.NOME AS NOME_PRODUTO , p.UNIDADE_MEDIDA FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id", 
		   countQuery = "SELECT COUNT(*) FROM CLIENTE c JOIN VENDA v on c.id=v.cliente_id JOIN PRODUTO p on v.produto_id=p.id", 
		   nativeQuery = true)	
	Page<Object> findAllPorDataColeta(Pageable pageable);	
	
	Page<Venda> findAll(Pageable pageable);
	
}
