package com.indra.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.indra.dto.ClienteDTO;
import com.indra.entity.Cliente;
import com.indra.response.Response;
import com.indra.service.ClienteService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("cliente")
@CrossOrigin(origins= "*")
public class ClienteController {
	
	@Autowired
	private ClienteService service;

	@Value("${paginacao.items_por_pagina}")
	private int qtdPorPagina;	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Response<ClienteDTO>> cadastrar(@Valid @RequestBody ClienteDTO dto, BindingResult result) {

		Response<ClienteDTO> response = new Response<ClienteDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setData(this.convertEntityToDto(service.save(this.convertDtoToEntity(dto))));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Response<ClienteDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteDTO dto, BindingResult result) {

		Optional<Cliente> cliente = service.findById(id);
		
		if ( cliente.isPresent() ) {
			
			Response<ClienteDTO> response = new Response<ClienteDTO>();
			
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
	public ResponseEntity<ClienteDTO> remover(@PathVariable Long id) {
		
		Optional<Cliente> cliente = service.findById(id);
		
		if ( cliente.isPresent() ) {
			service.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}	
	
	@GetMapping(path="buscarPorNome")
	public ResponseEntity<List<Cliente>> buscarClientePorNome(
			@Parameter(description = "Filtro para buscar por nome do cliente", required = true) String nomeCliente) {
		
		Optional<Cliente> cliente = service.findByNome(nomeCliente);
		
		if ( cliente.isPresent() ) {
			List<Cliente> nLista = new ArrayList<>();
			nLista.add(cliente.get());
			return new ResponseEntity<List<Cliente>>(nLista, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}	
	
	@GetMapping
	public ResponseEntity<Response<Page<Cliente>>> buscarTodos(
			@RequestParam(value = "pegarTodos", required = false, defaultValue = "false") Boolean pegarTodos,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir			
			) {
		
		Response<Page<Cliente>> response = new Response<Page<Cliente>>();
		PageRequest pageRequest = PageRequest.of(
				pegarTodos == true ? 0 : pag, 
				pegarTodos == true ? 999999999 : this.qtdPorPagina, 
				 Direction.valueOf(dir), ord);
		Page<Cliente> vp = service.findAll(pageRequest);
		response.setData(vp);
		return ResponseEntity.ok(response);	

	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<ClienteDTO>> buscarPorId(@PathVariable Long id) {
		
		Optional<Cliente> cliente = service.findById(id);
		
		if ( cliente.isPresent() ) {
			Response<ClienteDTO> response = new Response<ClienteDTO>();
			response.setData(this.convertEntityToDto(cliente.get()));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.notFound().build();
		
	}

	private Cliente convertDtoToEntity(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setInstalacaoCodigo(dto.getInstalacaoCodigo());
		cliente.setNome(dto.getNome());
		cliente.setSiglaRegiao(dto.getSiglaRegiao());
		cliente.setSiglaEstado(dto.getSiglaEstado());
		cliente.setMunicipio(dto.getMunicipio());
		
		return cliente;
	}
	
	private ClienteDTO convertEntityToDto(Cliente cliente) {
		
		ClienteDTO dto = new ClienteDTO();
		
		dto.setId(cliente.getId());
		dto.setInstalacaoCodigo(cliente.getInstalacaoCodigo());
		dto.setNome(cliente.getNome());
		dto.setSiglaRegiao(cliente.getSiglaRegiao());
		dto.setSiglaEstado(cliente.getSiglaEstado());
		dto.setMunicipio(cliente.getMunicipio());
		
		return dto;
	}	

}
