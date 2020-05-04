package com.indra.service;

import java.util.List;
import java.util.Optional;

import com.indra.dto.UserDTO;
import com.indra.entity.User;

public interface UserService {
	
	User save(User user);
	
	User update(Long id, UserDTO dto);
	
	void deleteById(Long id);
	
	List<User> findAll();
	
	Optional<User> findById(Long id);	

}
