package com.indra.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.indra.entity.HistoricoPreco;
import com.indra.entity.Produto;

public interface HistoricoPrecoService {
	
	HistoricoPreco save(HistoricoPreco historicoPreco);
	
	Page<HistoricoPreco> findAll(PageRequest pageRequest);
	
	Optional<HistoricoPreco> findById(Long id);
	
	Page<HistoricoPreco> listaHistoricoPreco(Long produtoId, PageRequest pageRequest);
	
	Produto findByProdutoId(Long id);

}
