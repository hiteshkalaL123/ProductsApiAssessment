package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.Entity.Item;

@Repository
public interface Itemrepository extends JpaRepository<Item,Long> {
	 List<Item> findByProductId(Long productId);

	  
	    boolean existsByProductId(Long productId);

	   
	    long countByProductId(Long productId);

}
