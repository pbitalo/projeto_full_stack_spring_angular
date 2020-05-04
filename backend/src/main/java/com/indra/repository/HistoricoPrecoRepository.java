package com.indra.repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.indra.entity.HistoricoPreco;

@NamedQueries({
	@NamedQuery(
	name = "HistoricoPrecoRepository.findByProdutoId",
			query = "SELECT hist FROM HISTORICO_PRECO hist WHERE hist.id = :produtoId")
})
public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Long> {
	
	Page<HistoricoPreco> findByProdutoId(Long produtoId, Pageable pageable);
	
	Page<HistoricoPreco> findAll(Pageable pageable);

}
