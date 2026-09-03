package com.product.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.Entity.Item;
import com.product.Entity.Product;
import com.product.Service.CustomUserDetailsService;
import com.product.Service.Itemservice;
import com.product.Service.JwtService;
import com.product.dto.itemrequest;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @MockitoBean
    private Itemservice itemService;

    private ObjectMapper objectMapper = new ObjectMapper();


    // ==========================================
    // 1. CREATE ITEM
    // ==========================================

    @Test
    void createItem_shouldCreateItem() throws Exception {

        itemrequest request = new itemrequest();
        request.setQuantity(10);

        Product product = new Product();
        product.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setQuantity(10);
        item.setProduct(product);

        when(itemService.createItem(eq(1L), any(Item.class)))
                .thenReturn(item);

        mockMvc.perform(
                post("/api/v1/products/1/items")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.productId").value(1))
        .andExpect(jsonPath("$.quantity").value(10));

        verify(itemService)
                .createItem(eq(1L), any(Item.class));
    }


    // ==========================================
    // 2. GET ALL ITEMS BY PRODUCT ID
    // ==========================================

    @Test
    void getItemsByProductId_shouldReturnItems() throws Exception {

        Product product = new Product();
        product.setId(1L);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setQuantity(10);
        item1.setProduct(product);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setQuantity(20);
        item2.setProduct(product);

        List<Item> items = Arrays.asList(item1, item2);

        when(itemService.getItemsByProductId(1L))
                .thenReturn(items);

        mockMvc.perform(
                get("/api/v1/products/1/items")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].productId").value(1))
        .andExpect(jsonPath("$[0].quantity").value(10))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].productId").value(1))
        .andExpect(jsonPath("$[1].quantity").value(20));

        verify(itemService)
                .getItemsByProductId(1L);
    }


    // ==========================================
    // 3. GET ITEM BY ID
    // ==========================================

    @Test
    void getItemById_shouldReturnItem() throws Exception {

        Product product = new Product();
        product.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setQuantity(10);
        item.setProduct(product);

        when(itemService.getItemById(1L))
                .thenReturn(item);

        mockMvc.perform(
                get("/api/v1/products/items/1")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.productId").value(1))
        .andExpect(jsonPath("$.quantity").value(10));

        verify(itemService)
                .getItemById(1L);
    }


    // ==========================================
    // 4. UPDATE ITEM
    // ==========================================

    @Test
    void updateItem_shouldUpdateItem() throws Exception {

        itemrequest request = new itemrequest();
        request.setQuantity(20);

        Product product = new Product();
        product.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setQuantity(20);
        item.setProduct(product);

        when(itemService.updateItem(1L, 20))
                .thenReturn(item);

        mockMvc.perform(
                put("/api/v1/products/items/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.productId").value(1))
        .andExpect(jsonPath("$.quantity").value(20));

        verify(itemService)
                .updateItem(1L, 20);
    }


    // ==========================================
    // 5. DELETE ITEM
    // ==========================================

    @Test
    void deleteItem_shouldDeleteItem() throws Exception {

        doNothing()
                .when(itemService)
                .deleteItem(1L);

        mockMvc.perform(
                delete("/api/v1/products/items/1")
        )
        .andExpect(status().isNoContent());

        verify(itemService)
                .deleteItem(1L);
    }
}