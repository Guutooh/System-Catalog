package br.com.dev.catalog.repository;

import br.com.dev.catalog.entities.Product;
import br.com.dev.catalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId = 2000L;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();

        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());

        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {


        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

//    @Test
//    public void deleteShouldThrowExceptionWhenIdDoesNotExist() {
//
//
//        Assertions.assertThrows(NoSuchElementException.class, () -> {
//            repository.deleteById(nonExistingId);
//        });
//
//    }



    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());

    }


    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {

        Optional<Product> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());

    }

}
