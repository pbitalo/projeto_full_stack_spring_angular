package com.indra.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.indra.dto.VendaDTO;
import com.indra.entity.Cliente;
import com.indra.entity.Produto;
import com.indra.entity.Venda;
import com.indra.repository.ClienteRepository;
import com.indra.repository.ProdutoRepository;
import com.indra.repository.VendaRepository;
import com.indra.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService {
	
	@Autowired
	VendaRepository repository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;	
	
	@Override
	public Venda save(Venda venda) {
		return repository.save(venda);
	}

	@Override
	public Page<Venda> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	@Override
	public Venda update(Long id, VendaDTO dto) {
		
		Venda venda = repository.getOne(id);
		venda.setDataColeta(dto.getDataColeta());
		venda.setValorCompra(dto.getValorCompra());
		venda.setValorVenda(dto.getValorVenda());
		venda.setBandeira(dto.getBandeira());
		venda.setCliente(clienteRepository.getOne(dto.getClienteId()));
		venda.setProduto(produtoRepository.getOne(dto.getProdutoId()));
		return venda;
		
	}

	@Override
	public Optional<Venda> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Cliente findByClienteId(Long id) {
		return clienteRepository.getOne(id);
	}
	
	@Override
	public Produto findByProdutoId(Long id) {
		return produtoRepository.getOne(id);
	}
	
	@Override 
	public Page<Object> findAllPorBandeira(PageRequest pageRequest, String nomeBandeira) {
		return repository.findAllPorBandeira(nomeBandeira , pageRequest);
	}
	
	@Override 
	public Page<Object> findAllPorDataColeta(PageRequest pageRequest) {
		return repository.findAllPorDataColeta(pageRequest);
	}	
	
	@Override
	public List<String> findByBandeira() {
		return repository.findByBandeira();
	}				
	
}
