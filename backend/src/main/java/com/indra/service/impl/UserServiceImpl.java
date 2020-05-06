package com.indra.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.indra.dto.UserDTO;
import com.indra.entity.User;
import com.indra.repository.UserRepository;
import com.indra.service.UserService;

@Service
public class UserServiceImpl  implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public Page<User> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}	

	@Override
	public User update(Long id, UserDTO dto) {
		User user = repository.getOne(id);
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}

	@Override
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
