package com.indra.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.indra.dto.ClienteDTO;
import com.indra.entity.Cliente;
import com.indra.entity.Venda;

public interface ClienteService {
	
	Cliente save(Cliente cliente);
	
	Cliente update(Long id, ClienteDTO dto);
	
	void deleteById(Long id);
	
	//List<Cliente> findAll();
	Page<Cliente> findAll(PageRequest pageRequest);
	
	Optional<Cliente> findById(Long id);
	
	Optional<Cliente> findByNome(String nome);
	
	BigDecimal buscarMediaPreco(String nomeMunicipio);
	
	BigDecimal buscarMediaPrecoCompra(String nomeMunicipio);
	
	BigDecimal buscarMediaPrecoPorBandeira(String nomeBandeira);
	
	BigDecimal buscarMediaPrecoCompraPorBandeira(String nomeBandeira);
	
	List<String> findByMunicipos();
	
	List<String> findByRegioes();
	
	List<Object> findAllPorRegiao(String siglaRegiao);

}
