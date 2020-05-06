package com.indra.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.indra.entity.HistoricoPreco;
import com.indra.entity.Produto;
import com.indra.repository.HistoricoPrecoRepository;
import com.indra.repository.ProdutoRepository;
import com.indra.service.HistoricoPrecoService;

@Service
public class HistoricoPrecoServiceImpl implements HistoricoPrecoService {
	
	@Autowired
	private HistoricoPrecoRepository repository;
	
	@Autowired
	ProdutoRepository produtoRepository;		
	
	@Override
	public HistoricoPreco save(HistoricoPreco historicoPreco) {
		return repository.save(historicoPreco);
	}

	@Override
	public Page<HistoricoPreco> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}	
	
	@Override
	public Optional<HistoricoPreco> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Produto findByProdutoId(Long id) {
		return produtoRepository.getOne(id);
	}

	@Override
	public Page<HistoricoPreco> listaHistoricoPreco(Long produtoId, PageRequest pageRequest) {
		return repository.findByProdutoId(produtoId, pageRequest);
	}		

}
