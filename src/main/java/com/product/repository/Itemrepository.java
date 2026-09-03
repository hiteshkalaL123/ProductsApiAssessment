package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.Entity.Item;

@Repository
public interface Itemrepository extends JpaRepository<Item,Long> {
	 List<Item> findByProductId(Long productId);

	    // Check whether an item exists for a product
	    boolean existsByProductId(Long productId);

	    // Count items belonging to a product
	    long countByProductId(Long productId);

}
