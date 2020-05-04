package com.indra.service;

import java.util.List;
import java.util.Optional;

import com.indra.dto.ProdutoDTO;
import com.indra.entity.Produto;

public interface ProdutoService {

	Produto save(Produto produto);
	
	Produto update(Long id, ProdutoDTO dto);
	
	void deleteById(Long id);
	
	List<Produto> findAll();
	
	Optional<Produto> findById(Long id);
	
	Optional<Produto> findByNome(String nome);	
	
}
