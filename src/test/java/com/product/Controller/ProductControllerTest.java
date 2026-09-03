package com.product.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.product.Service.CustomUserDetailsService;
import com.product.Service.JwtService;
import com.product.Service.Productservice;
import com.product.dto.productrequestdto;
import com.product.dto.productresponsesto;

@WebMvcTest(productcontroller.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Productservice productService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());


    // 1. GET ALL PRODUCTS
    @Test
    void getAllProducts_shouldReturnProducts() throws Exception {

    	productresponsesto product = new productresponsesto();

    	product.setId(1L);
    	product.setProductName("Laptop");
    	product.setCreatedBy("Hitesh");
    	product.setCreatedOn(LocalDateTime.now());
    	product.setModifiedBy(null);
    	product.setModifiedOn(null);

        Page<productresponsesto> page =
                new PageImpl<>(
                        Collections.singletonList(product),
                        PageRequest.of(0, 10),
                        1
                );

        when(productService.getAllProducts(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/v1/products")
                        .param("page", "0")
                        .param("size", "10")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].productName").value("Laptop"));

        verify(productService).getAllProducts(any());
    }

    // 2. GET PRODUCT BY ID
    @Test
    void getProductById_shouldReturnProduct() throws Exception {

        productresponsesto response = new productresponsesto();

        response.setId(1L);
        response.setProductName("Laptop");
        response.setCreatedBy("Hitesh");
        response.setCreatedOn(LocalDateTime.now());
        response.setModifiedBy(null);
        response.setModifiedOn(null);

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(
                get("/api/v1/products/1")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.productName").value("Laptop"));

        verify(productService).getProductById(1L);
    }


 // 3. CREATE PRODUCT

    @Test
    void createProduct_shouldCreateProduct() throws Exception {

        productrequestdto request = new productrequestdto();

        request.setProductName("Laptop");
        request.setCreatedBy("Hitesh");

        productresponsesto response = new productresponsesto();

        response.setId(1L);
        response.setProductName("Laptop");
        response.setCreatedBy("Hitesh");
        response.setCreatedOn(LocalDateTime.now());
        response.setModifiedBy(null);
        response.setModifiedOn(null);

        when(productService.createProduct(any(productrequestdto.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.productName").value("Laptop"));

        verify(productService)
                .createProduct(any(productrequestdto.class));
    }

    // 4. DELETE PRODUCT
    @Test
    void deleteProduct_shouldDeleteProduct() throws Exception {

        doNothing()
                .when(productService)
                .deleteProduct(1L);

        mockMvc.perform(
                delete("/api/v1/products/1")
        )
        .andExpect(status().isNoContent());

        verify(productService)
                .deleteProduct(1L);
    }
}

