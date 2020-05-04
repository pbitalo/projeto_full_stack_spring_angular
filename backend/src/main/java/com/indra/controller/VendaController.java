	package com.indra.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.dto.VendaDTO;
import com.indra.entity.HistoricoPreco;
import com.indra.entity.Venda;
import com.indra.response.Response;
import com.indra.service.HistoricoPrecoService;
import com.indra.service.VendaService;

@RestController
@RequestMapping("venda")
@CrossOrigin(origins= "*")
public class VendaController {
	
	@Autowired
	private VendaService service;
	
	@Autowired
	private HistoricoPrecoService serviceHistoricoPreco;
	
	@Value("${paginacao.items_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Response<VendaDTO>> cadastrar(@Valid @RequestBody VendaDTO dto, BindingResult result) {

		Response<VendaDTO> response = new Response<VendaDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		serviceHistoricoPreco.save(this.convertVendaDtoToHistoricoPrecoDto(dto));

		response.setData(this.convertEntityToDto(service.save(this.convertDtoToEntity(dto))));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Response<VendaDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid VendaDTO dto, BindingResult result) {

		Optional<Venda> venda = service.findById(id);
		
		if ( venda.isPresent() ) {
			
			Response<VendaDTO> response = new Response<VendaDTO>();
			
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			serviceHistoricoPreco.save(this.convertVendaDtoToHistoricoPrecoDto(dto));
			response.setData(this.convertEntityToDto(service.update(id , dto)));
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}
		
		return ResponseEntity.notFound().build();
		
	}
		
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<VendaDTO> remover(@PathVariable Long id) {
		
		Optional<Venda> venda = service.findById(id);
		
		if ( venda.isPresent() ) {
			service.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@GetMapping
	public ResponseEntity<Response<Page<Venda>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir
			) {
		
		Response<Page<Venda>> response = new Response<Page<Venda>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Venda> vp = service.findAll(pageRequest);
		response.setData(vp);
		return ResponseEntity.ok(response);
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<VendaDTO>> buscarPorId(@PathVariable Long id) {
		
		Optional<Venda> venda = service.findById(id);
		
		if ( venda.isPresent() ) {
			Response<VendaDTO> response = new Response<VendaDTO>();
			response.setData(this.convertEntityToDto(venda.get()));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	private Venda convertDtoToEntity(VendaDTO dto) {

		Venda venda = new Venda();
		venda.setId(dto.getId());
		venda.setDataColeta(dto.getDataColeta());
		venda.setValorCompra(dto.getValorCompra());
		venda.setValorVenda(dto.getValorVenda());
		venda.setBandeira(dto.getBandeira());
		venda.setCliente(service.findByClienteId(dto.getClienteId()));
		venda.setProduto(service.findByProdutoId(dto.getProdutoId()));
		
		return venda;
	}
	
	private HistoricoPreco convertVendaDtoToHistoricoPrecoDto(VendaDTO vendaDto) {
		
		HistoricoPreco histPrecodto = new HistoricoPreco();
		
		histPrecodto.setValorVenda(vendaDto.getValorVenda());
		histPrecodto.setProduto((service.findByProdutoId(vendaDto.getProdutoId())));
		histPrecodto.setDataColeta(LocalDateTime.now());
		
		return histPrecodto;
		
	}

	private VendaDTO convertEntityToDto(Venda venda) {

		VendaDTO dto = new VendaDTO();
		dto.setId(venda.getId());
		dto.setDataColeta(venda.getDataColeta());
		dto.setValorCompra(venda.getValorCompra());
		dto.setValorVenda(venda.getValorVenda());
		dto.setBandeira(venda.getBandeira());
		dto.setClienteId(venda.getCliente().getId());
		dto.setProdutoId(venda.getProduto().getId());

		return dto;
	}
	

}
