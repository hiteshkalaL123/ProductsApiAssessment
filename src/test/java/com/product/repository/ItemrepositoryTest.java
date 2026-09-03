package com.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.product.Entity.Item;
import com.product.Entity.Product;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class ItemRepositoryTest {

    @Autowired
    private Itemrepository itemRepository;

    @Autowired
    private productrepository productRepository;


    // ==========================================
    // 1. SAVE ITEM
    // ==========================================

    @Test
    void saveItem_shouldSaveItemSuccessfully() {

        Product product = new Product();
        product.setProductName("Laptop");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item = new Item();
        item.setQuantity(10);
        item.setProduct(savedProduct);

        Item savedItem = itemRepository.save(item);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getQuantity()).isEqualTo(10);
        assertThat(savedItem.getProduct().getId())
                .isEqualTo(savedProduct.getId());
    }


    // ==========================================
    // 2. FIND ITEM BY ID
    // ==========================================

    @Test
    void findById_shouldReturnItem() {

        Product product = new Product();
        product.setProductName("Mobile");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item = new Item();
        item.setQuantity(5);
        item.setProduct(savedProduct);

        Item savedItem = itemRepository.save(item);

        var result = itemRepository.findById(savedItem.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getQuantity()).isEqualTo(5);
        assertThat(result.get().getProduct().getId())
                .isEqualTo(savedProduct.getId());
    }


    // ==========================================
    // 3. FIND ITEMS BY PRODUCT ID
    // ==========================================

    @Test
    void findByProductId_shouldReturnItems() {

        Product product = new Product();
        product.setProductName("Keyboard");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item1 = new Item();
        item1.setQuantity(10);
        item1.setProduct(savedProduct);

        Item item2 = new Item();
        item2.setQuantity(20);
        item2.setProduct(savedProduct);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> items =
                itemRepository.findByProductId(savedProduct.getId());

        assertThat(items).hasSize(2);

        assertThat(items)
                .extracting(Item::getQuantity)
                .containsExactlyInAnyOrder(10, 20);
    }


    // ==========================================
    // 4. EXISTS BY PRODUCT ID
    // ==========================================

    @Test
    void existsByProductId_shouldReturnTrue() {

        Product product = new Product();
        product.setProductName("Monitor");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item = new Item();
        item.setQuantity(3);
        item.setProduct(savedProduct);

        itemRepository.save(item);

        boolean exists =
                itemRepository.existsByProductId(savedProduct.getId());

        assertThat(exists).isTrue();
    }


    // ==========================================
    // 5. COUNT BY PRODUCT ID
    // ==========================================

    @Test
    void countByProductId_shouldReturnCorrectCount() {

        Product product = new Product();
        product.setProductName("Mouse");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item1 = new Item();
        item1.setQuantity(5);
        item1.setProduct(savedProduct);

        Item item2 = new Item();
        item2.setQuantity(10);
        item2.setProduct(savedProduct);

        itemRepository.save(item1);
        itemRepository.save(item2);

        long count =
                itemRepository.countByProductId(savedProduct.getId());

        assertThat(count).isEqualTo(2);
    }


    // ==========================================
    // 6. DELETE ITEM
    // ==========================================

    @Test
    void deleteItem_shouldDeleteSuccessfully() {

        Product product = new Product();
        product.setProductName("Tablet");
        product.setCreatedBy("Hitesh");
        product.setCreatedOn(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        Item item = new Item();
        item.setQuantity(7);
        item.setProduct(savedProduct);

        Item savedItem = itemRepository.save(item);

        Long itemId = savedItem.getId();

        itemRepository.deleteById(itemId);

        var result = itemRepository.findById(itemId);

        assertThat(result).isEmpty();
    }
}