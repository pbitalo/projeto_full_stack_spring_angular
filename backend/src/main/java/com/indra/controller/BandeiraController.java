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
	

	@GetMapping
	public ResponseEntity<List<String>> buscarTodos() {
		List<String> regioes = service.findByBandeira();
		return new ResponseEntity<List<String>>(regioes, HttpStatus.OK);
	}
	
	
	@GetMapping(path = "buscarDadosPorBandeira")
	public ResponseEntity<List<Object>> buscarTodos(
			@Parameter(description = "Filtrar dados por bandeira", required = true) String nomeBandeira) {
		
		List<Object> dadosPorBandeira = service.findAllPorBandeira(nomeBandeira);
		return new ResponseEntity<List<Object>>(dadosPorBandeira, HttpStatus.OK);
		
	}		
	
	
	@GetMapping(path = "buscarDadosPorData")
	public ResponseEntity<List<Object>> buscarPorData(
			@Parameter(description = "Filtrar dados por data (ASC ou DESC)", required = true) String tipo) {
		List<Object> dadosPorData = service.findAllPorDataColeta(tipo);
		return new ResponseEntity<List<Object>>(dadosPorData, HttpStatus.OK);
	}
	
	
	@GetMapping(path = "buscarMediaPrecoCompra")
	public ResponseEntity<List<String>> buscarMediaPrecoCompra(
			@Parameter(description = "Filtro para buscar a média preço compra combustível", required = false) String nomeBandeira) {
		
		BigDecimal valorMedia = serviceCliente.buscarMediaPrecoCompraPorBandeira(nomeBandeira);
		List<String> listaRetorno = new ArrayList<String>();
		listaRetorno.add(valorMedia+"");
		return new ResponseEntity<List<String>>(listaRetorno, HttpStatus.OK);
		
	}	
	
	
	@GetMapping(path = "buscarMediaPrecoVenda")
	public ResponseEntity<List<String>> buscarMediaPrecoCompraVenda(
			@Parameter(description = "Filtro para buscar a média preço venda combustível", required = false) String nomeBandeira) {
		
		BigDecimal valorMedia = serviceCliente.buscarMediaPrecoPorBandeira(nomeBandeira);
		List<String> listaRetorno = new ArrayList<String>();
		listaRetorno.add(valorMedia+"");
		return new ResponseEntity<List<String>>(listaRetorno, HttpStatus.OK);
		
	}		

}
