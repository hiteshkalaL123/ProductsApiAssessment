package com.product.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.product.Entity.Item;
import com.product.Entity.Product;
import com.product.Exception.ResourceNotFoundException;
import com.product.Service.Itemservice;
import com.product.repository.Itemrepository;
import com.product.repository.productrepository;


@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private  Itemrepository itemRepository;

    @Mock
    private  productrepository productRepository;

    @InjectMocks
    private Itemservice itemService;

    @Test
    void createItem_shouldCreateItem() {

        Product product = new Product();
        product.setId(1L);

        Item item = new Item();
        item.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(itemRepository.save(any(Item.class)))
                .thenReturn(item);

        Item result =
                itemService.createItem(1L, item);

        assertNotNull(result);
        assertEquals(10, result.getQuantity());

        verify(productRepository)
                .findById(1L);

        verify(itemRepository)
                .save(item);
    }

    @Test
    void getItemById_shouldReturnItem() {

        Item item = new Item();
        item.setId(1L);
        item.setQuantity(10);

        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        Item result =
                itemService.getItemById(1L);

        assertNotNull(result);

        assertEquals(
                10,
                result.getQuantity()
        );
    }

    public Item getItemById(Long id) {

        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Item with id " + id + " not found"
                        ));
    }

    @Test
    void deleteItem_shouldDeleteItem() {

        Item item = new Item();
        item.setId(1L);

        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        itemService.deleteItem(1L);

        verify(itemRepository)
                .delete(item);
    }
}
