package com.indra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.indra.entity.Regiao;
import com.indra.service.ClienteService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("regiao")
@CrossOrigin(origins= "*")
public class RegiaoController {
	
	@Autowired
	private ClienteService service;

	
	@GetMapping
	public ResponseEntity<List<String>> buscarTodos() {
		List<String> regioes = service.findByRegioes();
		return new ResponseEntity<List<String>>(regioes, HttpStatus.OK);
	}
	
	
	@GetMapping(path = "buscarDadosPorRegiao")
	public ResponseEntity<List<Object>> buscarTodos(
			@Parameter(description = "Filtro dados por região", required = true) String siglaRegiao) {
		
		List<Object> dadosPorRegiao = service.findAllPorRegiao(siglaRegiao);
		return new ResponseEntity<List<Object>>(dadosPorRegiao, HttpStatus.OK);
		
	}	

}
