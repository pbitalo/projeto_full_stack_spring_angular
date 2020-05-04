package com.indra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indra.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	Optional<Produto> findByNomeEquals(String nome);
	
}
