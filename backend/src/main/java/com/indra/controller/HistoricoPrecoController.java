package com.indra.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.dto.HistoricoPrecoDTO;
import com.indra.entity.HistoricoPreco;
import com.indra.response.Response;
import com.indra.service.HistoricoPrecoService;

@RestController
@RequestMapping("historicoPreco")
@CrossOrigin(origins= "*")
public class HistoricoPrecoController {
	
	@Autowired
	private HistoricoPrecoService service;
	
	@Value("${paginacao.items_por_pagina}")
	private int qtdPorPagina;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Response<HistoricoPrecoDTO>> cadastrar(@Valid @RequestBody HistoricoPrecoDTO dto, BindingResult result) {

		Response<HistoricoPrecoDTO> response = new Response<HistoricoPrecoDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		response.setData(this.convertEntityToDto(service.save(this.convertDtoToEntity(dto))));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}	
	
	
	@GetMapping
	public ResponseEntity<Response<Page<HistoricoPreco>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir ) {
		
		
		Response<Page<HistoricoPreco>> response = new Response<Page<HistoricoPreco>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<HistoricoPreco> historico = service.findAll(pageRequest);
		response.setData(historico);
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("listaHistoricoPreco/{id}")
	public ResponseEntity<Response<Page<HistoricoPreco>>> buscarHistoricoProdutoId(
			@PathVariable Long id,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir 			
			) {
		
		Response<Page<HistoricoPreco>> response = new Response<Page<HistoricoPreco>>();
		
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);		
		
		Page<HistoricoPreco> historicoPreco = service.listaHistoricoPreco(id, pageRequest);
		response.setData(historicoPreco);
		return ResponseEntity.ok(response);		
		/*
		if ( historicoPreco.isPresent() ) {
			Response<Page<HistoricoPreco>> response = new Response<Page<HistoricoPreco>>();
			response.setData(historicoPreco.get());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}*/
		
//		return ResponseEntity.notFound().build();
		
	}		
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<HistoricoPrecoDTO>> buscarPorId(@PathVariable Long id) {
		
		Optional<HistoricoPreco> historicoPreco = service.findById(id);
		
		if ( historicoPreco.isPresent() ) {
			Response<HistoricoPrecoDTO> response = new Response<HistoricoPrecoDTO>();
			response.setData(this.convertEntityToDto(historicoPreco.get()));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	private HistoricoPreco convertDtoToEntity(HistoricoPrecoDTO dto) {
		HistoricoPreco historicoPreco = new HistoricoPreco();
		historicoPreco.setId(dto.getId());
		historicoPreco.setDataColeta(dto.getDataColeta());
		historicoPreco.setValorVenda(dto.getValorVenda());
		historicoPreco.setProduto(service.findByProdutoId(dto.getProdutoId()));
		return historicoPreco;
	}
	
	private HistoricoPrecoDTO convertEntityToDto(HistoricoPreco historicoPreco) {
		HistoricoPrecoDTO dto = new HistoricoPrecoDTO();
		dto.setId(historicoPreco.getId());
		dto.setDataColeta(historicoPreco.getDataColeta());
		dto.setValorVenda(historicoPreco.getValorVenda());
		dto.setProdutoId(historicoPreco.getProduto().getId());
		return dto;
	}		

}
