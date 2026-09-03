package com.product.Controller;

import com.product.Service.Productservice;

import com.product.dto.productrequestdto;
import com.product.dto.productresponsesto;

import jakarta.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class productcontroller {

	@Autowired
    private  Productservice productService;

    // CREATE
    @PostMapping
    public ResponseEntity<productresponsesto> createProduct(
            @Valid @RequestBody productrequestdto request) {

        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<productresponsesto>> getAllProducts(
            Pageable pageable) {

        return ResponseEntity.ok(
                productService.getAllProducts(pageable)
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<productresponsesto> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<productresponsesto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody productrequestdto request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}