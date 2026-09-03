package com.product.Controller;

import com.product.Entity.Item;
import com.product.Service.Itemservice;
import com.product.dto.itemrequest;
import com.product.dto.itemresponsedto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")

public class ItemController {

	@Autowired
    private  Itemservice itemService;

    // ==========================================
    // GET ALL ITEMS OF A PRODUCT
    // ==========================================

    @GetMapping("/{productId}/items")
    public ResponseEntity<List<itemresponsedto>> getItemsByProductId(
            @PathVariable Long productId) {

        List<Item> items =
                itemService.getItemsByProductId(productId);

        List<itemresponsedto> response = items.stream()
                .map(this::convertToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ==========================================
    // CREATE ITEM
    // ==========================================

    @PostMapping("/{productId}/items")
    public ResponseEntity<itemresponsedto> createItem(
            @PathVariable Long productId,
            @Valid @RequestBody itemrequest request) {

        Item item = new Item();
        item.setQuantity(request.getQuantity());

        Item savedItem =
                itemService.createItem(productId, item);

        return new ResponseEntity<>(
                convertToResponse(savedItem),
                HttpStatus.CREATED
        );
    }

    // ==========================================
    // GET ITEM BY ID
    // ==========================================

    @GetMapping("/items/{itemId}")
    public ResponseEntity<itemresponsedto> getItemById(
            @PathVariable Long itemId) {

        Item item = itemService.getItemById(itemId);

        return ResponseEntity.ok(
                convertToResponse(item)
        );
    }

    // ==========================================
    // UPDATE ITEM
    // ==========================================

    @PutMapping("/items/{itemId}")
    public ResponseEntity<itemresponsedto> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody itemrequest request) {

        Item updatedItem =
                itemService.updateItem(
                        itemId,
                        request.getQuantity()
                );

        return ResponseEntity.ok(
                convertToResponse(updatedItem)
        );
    }

    // ==========================================
    // DELETE ITEM
    // ==========================================

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId) {

        itemService.deleteItem(itemId);

        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // ENTITY → DTO
    // ==========================================

    private itemresponsedto convertToResponse(Item item) {

    	itemresponsedto response = new itemresponsedto();

        response.setId(item.getId());
        response.setProductId(item.getProduct().getId());
        response.setQuantity(item.getQuantity());

        return response;
    }
}