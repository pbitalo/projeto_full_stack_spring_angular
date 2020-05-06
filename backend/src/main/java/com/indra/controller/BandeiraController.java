package com.indra.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indra.response.Response;
import com.indra.service.ClienteService;
import com.indra.service.VendaService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("bandeira")
@CrossOrigin(origins= "*")
public class BandeiraController {
	
	@Autowired
	private VendaService service;
	
	@Autowired
	private ClienteService serviceCliente;
	
	@Value("${paginacao.items_por_pagina}")
	private int qtdPorPagina;	

	@GetMapping
	public ResponseEntity<Response<List<String>>> buscarTodos() {
		Response<List<String>> response = new Response<List<String>>();
		List<String> regioes = service.findByBandeira();
		response.setData(regioes);
		return ResponseEntity.ok(response);		
	}
	
	@GetMapping(path = "buscarDadosPorBandeira")
	public ResponseEntity<Response<Page<Object>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir,			
			@Parameter(description = "Filtrar dados por bandeira", required = false) String nomeBandeira) {
		
		Response<Page<Object>> response = new Response<Page<Object>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Object> vp = service.findAllPorBandeira(pageRequest, nomeBandeira);
		response.setData(vp);
		return ResponseEntity.ok(response);		
		
	}		
	
	
	@GetMapping(path = "buscarDadosPorData")
	public ResponseEntity<Response<Page<Object>>> buscarPorData(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		
		Response<Page<Object>> response = new Response<Page<Object>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Object> dadosPorData = service.findAllPorDataColeta(pageRequest);
		response.setData(dadosPorData);
		return ResponseEntity.ok(response);				
		
	}
	
	
	@GetMapping(path = "buscarMediaPrecoCompra")
	public ResponseEntity<Response<List<String>>> buscarMediaPrecoCompra(
			@Parameter(description = "Filtro para buscar a média preço compra combustível", required = false) String nomeBandeira) {
		
		Response<List<String>> response = new Response<List<String>>();
		BigDecimal valorMedia = serviceCliente.buscarMediaPrecoCompraPorBandeira(nomeBandeira);
		List<String> listaRetorno = new ArrayList<String>();
		listaRetorno.add(valorMedia+"");
		response.setData(listaRetorno);
		return ResponseEntity.ok(response);
		
	}	
	
	@GetMapping(path = "buscarMediaPrecoVenda")
	public ResponseEntity<Response<List<String>>> buscarMediaPrecoCompraVenda(
			@Parameter(description = "Filtro para buscar a média preço venda combustível", required = false) String nomeBandeira) {
		
		Response<List<String>> response = new Response<List<String>>();
		BigDecimal valorMedia = serviceCliente.buscarMediaPrecoPorBandeira(nomeBandeira);
		List<String> listaRetorno = new ArrayList<String>();
		listaRetorno.add(valorMedia+"");
		response.setData(listaRetorno);
		return ResponseEntity.ok(response);
		
	}		

}
