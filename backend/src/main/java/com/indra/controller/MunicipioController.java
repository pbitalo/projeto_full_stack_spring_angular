package com.indra.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indra.response.Response;
import com.indra.service.ClienteService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("municipio")
@CrossOrigin(origins= "*")
public class MunicipioController {
	
	@Autowired
	private ClienteService service;
	
	
	@GetMapping
	public ResponseEntity<Response<List<String>>> buscarTodos(
			@Parameter(description = "Filtro para buscar a média preço combustível", required = false) String nomeMunicipio ) {
		
		Response<List<String>> response = new Response<List<String>>();
		
		if (nomeMunicipio == null) {
			List<String> municipios = service.findByMunicipos();
			response.setData(municipios);
			return ResponseEntity.ok(response);			
		} else {
			BigDecimal valorMedia = service.buscarMediaPreco(nomeMunicipio);
			List<String> listaRetorno = new ArrayList<String>();
			listaRetorno.add(valorMedia+"");
			response.setData(listaRetorno);
		}
		
		return ResponseEntity.ok(response);
		
	}
	
	
	@GetMapping(path = "buscarMediaPrecoCompra")
	public ResponseEntity<List<String>> buscarMediaPrecoCompra(
			@Parameter(description = "Filtro para buscar a média preço compra combustível", required = false) String nomeMunicipio ) {
		
		BigDecimal valorMedia = service.buscarMediaPrecoCompra(nomeMunicipio);
		List<String> listaRetorno = new ArrayList<String>();
		listaRetorno.add(valorMedia+"");
		return new ResponseEntity<List<String>>(listaRetorno, HttpStatus.OK);
		
	}	

}
