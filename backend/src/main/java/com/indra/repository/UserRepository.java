package com.indra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.indra.entity.User;

public interface UserRepository extends JpaRepository<User, Long> { 

	Page<User> findAll(Pageable pageable);
	
}
