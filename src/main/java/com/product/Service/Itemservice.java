package com.product.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.Entity.Item;
import com.product.Entity.Product;
import com.product.repository.Itemrepository;
import com.product.repository.productrepository;

@Service
public class Itemservice {
	@Autowired
	private  Itemrepository itemRepository;
	
	@Autowired
    private  productrepository productRepository;

    // CREATE ITEM
    public Item createItem(Long productId, Item item) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product with id " + productId + " not found"
                        )
                );

        item.setProduct(product);

        return itemRepository.save(item);
    }

    // GET ALL ITEMS OF A PRODUCT
    public List<Item> getItemsByProductId(Long productId) {

        // First check whether product exists
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException(
                    "Product with id " + productId + " not found"
            );
        }

        return itemRepository.findByProductId(productId);
    }

    // GET ITEM BY ID
    public Item getItemById(Long id) {

        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Item with id " + id + " not found"
                        )
                );
    }

    // UPDATE ITEM
    public Item updateItem(Long id, Integer quantity) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Item with id " + id + " not found"
                        )
                );

        item.setQuantity(quantity);

        return itemRepository.save(item);
    }

    // DELETE ITEM
    public void deleteItem(Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Item with id " + id + " not found"
                        )
                );

        itemRepository.delete(item);
    }

}
