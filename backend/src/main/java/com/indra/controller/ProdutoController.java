package com.indra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.indra.dto.ProdutoDTO;
import com.indra.entity.Produto;
import com.indra.response.Response;
import com.indra.service.ProdutoService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("produto")
@CrossOrigin(origins= "*")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	
	@PostMapping
	@Transactional
	public ResponseEntity<Response<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO dto, BindingResult result) {

		Response<ProdutoDTO> response = new Response<ProdutoDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setData(this.convertEntityToDto(service.save(this.convertDtoToEntity(dto))));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Response<ProdutoDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoDTO dto, BindingResult result) {
		Optional<Produto> produto = service.findById(id);
		
		if ( produto.isPresent() ) {
			
			Response<ProdutoDTO> response = new Response<ProdutoDTO>();
			
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			response.setData(this.convertEntityToDto(service.update(id , dto)));
			return ResponseEntity.status(HttpStatus.OK).body(response);
			
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
		
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ProdutoDTO> remover(@PathVariable Long id) {
		
		Optional<Produto> produto = service.findById(id);
		
		if ( produto.isPresent() ) {
			service.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}	

	
	@GetMapping
	public ResponseEntity<Response<List<Produto>>> buscarTodos(
			@Parameter(description = "Filtro para buscar por nome do produto", required = false) String nomeProduto ) {
		
		Response<List<Produto>> response = new Response<List<Produto>>();
		
		if (nomeProduto == null) {
			
			response.setData(service.findAll());
			return ResponseEntity.ok(response);
			
		} else {
			
			Optional<Produto> produto = service.findByNome(nomeProduto);
			
			if ( produto.isPresent() ) {
				
				List<Produto> nLista = new ArrayList<>();
				nLista.add(produto.get());
				response.setData(nLista);
				return ResponseEntity.ok(response);
				
			}			
			
		}
		
		return ResponseEntity.notFound().build();
		
	}	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<ProdutoDTO>> buscarPorId(@PathVariable Long id) {
		
		Optional<Produto> produto = service.findById(id);
		
		if ( produto.isPresent() ) {
			Response<ProdutoDTO> response = new Response<ProdutoDTO>();
			response.setData(this.convertEntityToDto(produto.get()));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	private Produto convertDtoToEntity(ProdutoDTO dto) {

		Produto produto = new Produto();
		produto.setId(dto.getId());
		produto.setNome(dto.getNome());
		produto.setPreco(dto.getPreco());
		produto.setUnidadeMedida(dto.getUnidadeMedida());

		return produto;
	}

	private ProdutoDTO convertEntityToDto(Produto produto) {

		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setPreco(produto.getPreco());
		dto.setUnidadeMedida(produto.getUnidadeMedida());

		return dto;
	}

}
