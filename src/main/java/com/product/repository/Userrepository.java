package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.Entity.User;

@Repository
public interface Userrepository extends JpaRepository<User, Long> {
	 Optional<User> findByUsername(String username);

	    boolean existsByUsername(String username);

}
