
package com.product.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.product.Entity.Product;
import com.product.Exception.ResourceNotFoundException;
import com.product.Service.Productservice;
import com.product.dto.productrequestdto;
import com.product.dto.productresponsesto;
import com.product.repository.productrepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private productrepository productRepository;

    @InjectMocks
    private Productservice productService;

    private Product product;

    @BeforeEach
    void setUp() {

        product = new Product();

        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());
    }

    @Test
    void createProduct_shouldCreateProduct() {

    	productrequestdto request = new productrequestdto();

        request.setProductName("Laptop");
        request.setCreatedBy("Hitesh");

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        productresponsesto response =
                productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getProductName());

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productresponsesto response =
                productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Laptop", response.getProductName());

        verify(productRepository)
                .findById(1L);
    }

    @Test
    void getProductById_shouldThrowExceptionWhenNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(99L)
        );

        verify(productRepository)
                .findById(99L);
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository)
                .delete(product);
    }
}