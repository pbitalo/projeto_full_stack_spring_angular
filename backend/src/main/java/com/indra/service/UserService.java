package com.indra.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.indra.dto.UserDTO;
import com.indra.entity.User;

public interface UserService {
	
	User save(User user);
	
	User update(Long id, UserDTO dto);
	
	void deleteById(Long id);
	
	Page<User> findAll(PageRequest pageRequest);
	
	Optional<User> findById(Long id);

}
