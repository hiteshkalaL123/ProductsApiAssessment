package com.product.Service;

import com.product.Entity.Product;
import com.product.Exception.ResourceNotFoundException;


import com.product.dto.productrequestdto;
import com.product.dto.productresponsesto;
import com.product.repository.productrepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Productservice {

	@Autowired
    private  productrepository productRepository;

    // CREATE
    public productresponsesto createProduct(productrequestdto request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setCreatedBy(request.getCreatedBy());
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return convertToResponse(savedProduct);
    }

    // GET ALL
    public Page<productresponsesto> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    // GET BY ID
    public productresponsesto getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        return convertToResponse(product);
    }

    // UPDATE
    public productresponsesto updateProduct(
            Long id,
            productrequestdto request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        existingProduct.setProductName(request.getProductName());
        existingProduct.setModifiedBy(request.getModifiedBy());
        existingProduct.setModifiedOn(LocalDateTime.now());

        Product updatedProduct =
                productRepository.save(existingProduct);

        return convertToResponse(updatedProduct);
    }

    // DELETE
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        productRepository.delete(product);
    }

    // ENTITY -> DTO
    private productresponsesto convertToResponse(Product product) {

    	productresponsesto response = new productresponsesto();

        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setCreatedBy(product.getCreatedBy());
        response.setCreatedOn(product.getCreatedOn());
        response.setModifiedBy(product.getModifiedBy());
        response.setModifiedOn(product.getModifiedOn());

        return response;
    }
}