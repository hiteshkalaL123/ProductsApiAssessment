package com.product.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.dto.productrequestdto;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createProduct_shouldReturnCreated() throws Exception {

        productrequestdto request = new productrequestdto();

        request.setProductName("Laptop");
        request.setCreatedBy("Hitesh");

        mockMvc.perform(
                post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.productName").value("Laptop"))
        .andExpect(jsonPath("$.createdBy").value("Hitesh"));
    }
    @Test
    void getAllProducts_shouldReturnOk() throws Exception {

        mockMvc.perform(
                get("/api/v1/products")
        )
        .andExpect(status().isOk());
    }
    
    @Test
    void getProductById_shouldReturnOk() throws Exception {

        productrequestdto request = new productrequestdto();

        request.setProductName("Mobile");
        request.setCreatedBy("Hitesh");

        String response =
                mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long productId =
                objectMapper.readTree(response)
                        .get("id")
                        .asLong();

        mockMvc.perform(
                get("/api/v1/products/" + productId)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(productId))
        .andExpect(jsonPath("$.productName").value("Mobile"));
    }


    // ==========================================
    // 4. UPDATE PRODUCT
    // ==========================================

    @Test
    void updateProduct_shouldReturnOk() throws Exception {

        productrequestdto createRequest =
                new productrequestdto();

        createRequest.setProductName("Old Laptop");
        createRequest.setCreatedBy("Hitesh");

        String response =
                mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                createRequest
                                        )
                                )
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long productId =
                objectMapper.readTree(response)
                        .get("id")
                        .asLong();


        productrequestdto updateRequest =
                new productrequestdto();

        updateRequest.setProductName("New Laptop");
        updateRequest.setCreatedBy("Hitesh");
        updateRequest.setModifiedBy("Admin");


        mockMvc.perform(
                put("/api/v1/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        updateRequest
                                )
                        )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(productId))
        .andExpect(jsonPath("$.productName").value("New Laptop"))
        .andExpect(jsonPath("$.modifiedBy").value("Admin"));
    }
    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {

        productrequestdto request =
                new productrequestdto();

        request.setProductName("Tablet");
        request.setCreatedBy("Hitesh");

        String response =
                mockMvc.perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long productId =
                objectMapper.readTree(response)
                        .get("id")
                        .asLong();


        mockMvc.perform(
                delete("/api/v1/products/" + productId)
        )
        .andExpect(status().isNoContent());


        mockMvc.perform(
                get("/api/v1/products/" + productId)
        )
        .andExpect(status().isNotFound());
    }
}
