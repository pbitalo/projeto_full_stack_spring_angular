package com.indra.controller;

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

import com.indra.dto.UserDTO;
import com.indra.entity.User;
import com.indra.response.Response;
import com.indra.service.UserService;
//import com.indra.util.Bcrypt;

@RestController
@RequestMapping("user")
@CrossOrigin(origins= "*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Response<UserDTO>> cadastrar(@Valid @RequestBody UserDTO dto, BindingResult result) {

		Response<UserDTO> response = new Response<UserDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erros -> response.getErros().add(erros.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setData(this.convertEntityToDto(service.save(this.convertDtoToEntity(dto))));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Response<UserDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid UserDTO dto, BindingResult result) {

		Optional<User> user = service.findById(id);
		
		if ( user.isPresent() ) {
			
			Response<UserDTO> response = new Response<UserDTO>();
			
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
	public ResponseEntity<UserDTO> remover(@PathVariable Long id) {
		
		Optional<User> user = service.findById(id);
		
		if ( user.isPresent() ) {
			service.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}	

	
	@GetMapping
	public ResponseEntity<List<User>> buscarTodos() {
		List<User> vp = service.findAll();
		return new ResponseEntity<List<User>>(vp, HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<UserDTO>> buscarPorId(@PathVariable Long id) {
		
		Optional<User> user = service.findById(id);
		
		if ( user.isPresent() ) {
			Response<UserDTO> response = new Response<UserDTO>();
			response.setData(this.convertEntityToDto(user.get()));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	private User convertDtoToEntity(UserDTO dto) {
		
		User user = new User();
		user.setId(dto.getId());
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		//user.setPassword(Bcrypt.getHash(dto.getPassword()));
		
		return user;
		
	}
	
	private UserDTO convertEntityToDto(User user) {
		
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());
		
		return dto;
		
	}

}
