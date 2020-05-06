package com.indra.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.indra.dto.ClienteDTO;
import com.indra.entity.Cliente;
import com.indra.repository.ClienteRepository;
import com.indra.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	@Override
	public Page<Cliente> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}	
	
	@Override
	public Cliente update(Long id, ClienteDTO dto) {
		
		Cliente cliente = repository.getOne(id);
		
		cliente.setNome(dto.getNome());
		cliente.setInstalacaoCodigo(dto.getInstalacaoCodigo());
		cliente.setSiglaEstado(dto.getSiglaEstado());
		cliente.setSiglaRegiao(dto.getSiglaRegiao());
		cliente.setMunicipio(dto.getMunicipio());
		
		return cliente;
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente;
	}
	
	@Override
	public Optional<Cliente> findByNome(String nome) {
		Optional<Cliente> cliente = repository.findByNomeEquals(nome);
		return cliente;
	}	
	
	@Override
	public List<String> findByMunicipos() {
		List<String> municipios = repository.findByMunicipos();
		return municipios;
	}		
	
	@Override
	public List<String> findByRegioes() {
		List<String> regioes = repository.findByRegioes();
		return regioes;
	}			
	
	@Override
	public BigDecimal buscarMediaPreco(String nomeMunicipio) {
		return repository.findMediaPrecoCombustivel(nomeMunicipio);
	}	
	
	@Override
	public BigDecimal buscarMediaPrecoCompra(String nomeMunicipio) {
		return repository.findMediaCompraCombustivel(nomeMunicipio);
	}

	@Override
	public BigDecimal buscarMediaPrecoPorBandeira(String nomeBandeira) {
		return repository.findMediaPrecoCombustivelPorBandeira(nomeBandeira);
	}

	@Override
	public BigDecimal buscarMediaPrecoCompraPorBandeira(String nomeBandeira) {
		return repository.findMediaCompraCombustivelPorBandeira(nomeBandeira);
	}	
	
	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
  
	@Override
	public  Page<Object> findAllPorRegiao(PageRequest pageRequest, String siglaRegiao) {
		return repository.findDataPorRegioes(pageRequest, siglaRegiao);
	}

}
