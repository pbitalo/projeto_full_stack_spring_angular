package com.indra.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indra.dto.ProdutoDTO;
import com.indra.entity.Produto;
import com.indra.repository.ProdutoRepository;
import com.indra.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;	

	@Override
	public Produto save(Produto produto) {
		return repository.save(produto);
	}

	@Override
	public List<Produto> findAll() {
		return repository.findAll();
	}

	@Override
	public Produto update(Long id, ProdutoDTO dto) {
		
		Produto produto = repository.getOne(id);
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		produto.setUnidadeMedida(dto.getUnidadeMedida());
		
		return produto;
	}

	@Override
	public Optional<Produto> findById(Long id) {
		Optional<Produto> produto = repository.findById(id);
		return produto;
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	public Optional<Produto> findByNome(String nome) {
		Optional<Produto> produto = repository.findByNomeEquals(nome);
		return produto;
	}		

}
