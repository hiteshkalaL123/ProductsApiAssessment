package com.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.product.Entity.Product;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class productrepositorytest {

    @Autowired
    private productrepository productRepository;


    // ==========================================
    // 1. SAVE PRODUCT
    // ==========================================

    @Test
    void saveProduct_shouldSaveSuccessfully() {

        Product product = new Product();

        product.setProductName("Laptop");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName())
                .isEqualTo("Laptop");
        assertThat(savedProduct.getCreatedBy())
                .isEqualTo("Hitesh");
    }


    // ==========================================
    // 2. FIND PRODUCT BY ID
    // ==========================================

    @Test
    void findById_shouldReturnProduct() {

        Product product = new Product();

        product.setProductName("Mobile");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        var result =
                productRepository.findById(savedProduct.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getProductName())
                .isEqualTo("Mobile");

        assertThat(result.get().getCreatedBy())
                .isEqualTo("Hitesh");
    }


    // ==========================================
    // 3. FIND ALL PRODUCTS
    // ==========================================

    @Test
    void findAll_shouldReturnProducts() {

        Product product1 = new Product();

        product1.setProductName("Laptop");
        product1.setCreatedBy("Hitesh");
        product1.setCreatedOn(LocalDateTime.now());


        Product product2 = new Product();

        product2.setProductName("Mobile");
        product2.setCreatedBy("Rahul");
        product2.setCreatedOn(LocalDateTime.now());


        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products =
                productRepository.findAll();

        assertThat(products).hasSize(2);
    }




    @Test
    void updateProduct_shouldUpdateSuccessfully() {

        Product product = new Product();

        product.setProductName("Old Laptop");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct =
                productRepository.save(product);


        savedProduct.setProductName("New Laptop");
        savedProduct.setModifiedBy("Admin");
        savedProduct.setModifiedOn(LocalDateTime.now());

        Product updatedProduct =
                productRepository.save(savedProduct);


        assertThat(updatedProduct.getProductName())
                .isEqualTo("New Laptop");

        assertThat(updatedProduct.getModifiedBy())
                .isEqualTo("Admin");
    }


    // ==========================================
    // 5. DELETE PRODUCT
    // ==========================================

    @Test
    void deleteProduct_shouldDeleteSuccessfully() {

        Product product = new Product();

        product.setProductName("Tablet");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct =
                productRepository.save(product);

        Long productId = savedProduct.getId();


        productRepository.deleteById(productId);


        var result =
                productRepository.findById(productId);

        assertThat(result).isEmpty();
    }


   

    @Test
    void existsById_shouldReturnTrue() {

        Product product = new Product();

        product.setProductName("Monitor");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct =
                productRepository.save(product);


        boolean exists =
                productRepository.existsById(savedProduct.getId());


        assertThat(exists).isTrue();
    }
}
