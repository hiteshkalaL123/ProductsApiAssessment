package com.product.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.Entity.Product;

@Repository
public interface productrepository extends JpaRepository<Product, Long> {
	
	 // Find product by name
    List<Product> findByProductName(String productName);

    // Find products whose name contains given text
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    // Check whether product exists by name
    boolean existsByProductName(String productName);

    // Find products created by a particular user
    List<Product> findByCreatedBy(String createdBy);

    // Pagination
    Page<Product> findAll(Pageable pageable);
}
