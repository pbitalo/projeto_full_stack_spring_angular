package com.indra.controller;

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
//import com.indra.entity.Regiao;
import com.indra.service.ClienteService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("regiao")
@CrossOrigin(origins= "*")
public class RegiaoController {
	
	@Autowired
	private ClienteService service;
	
	@Value("${paginacao.items_por_pagina}")
	private int qtdPorPagina;	
	
	@GetMapping
	public ResponseEntity<Response<List<String>>> buscarTodos() {
		Response<List<String>> response = new Response<List<String>>();
		List<String> regioes = service.findByRegioes();
		response.setData(regioes);
		return ResponseEntity.ok(response);		
	}
	
	@GetMapping(path = "buscarDadosPorRegiao")
	public ResponseEntity<Response<Page<Object>>> buscarTodos(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir,			
			@Parameter(description = "Filtro dados por região", required = true) String siglaRegiao) {
		
		Response<Page<Object>> response = new Response<Page<Object>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		
		Page<Object> dadosPorRegiao = service.findAllPorRegiao(pageRequest, siglaRegiao);
		response.setData(dadosPorRegiao);
		return ResponseEntity.ok(response);		
		
	}	

}
