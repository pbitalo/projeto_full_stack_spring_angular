package com.indra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.indra.dto.VendaDTO;
import com.indra.entity.Cliente;
import com.indra.entity.Produto;
import com.indra.entity.Venda;

public interface VendaService {
	
	Venda save(Venda venda);
	
	Venda update(Long id, VendaDTO dto);
	
	void deleteById(Long id);
	
	Page<Venda> findAll(PageRequest pageRequest);
	
	Optional<Venda> findById(Long id);	
	
	Cliente findByClienteId(Long id);
	
	Produto findByProdutoId(Long id);
	
	List<String> findByBandeira();
	
	Page<Object> findAllPorBandeira(PageRequest pageRequest, String nomeBandeira);
	
	Page<Object> findAllPorDataColeta(PageRequest pageRequest);

}
