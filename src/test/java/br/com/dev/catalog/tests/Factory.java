package br.com.dev.catalog.tests;

import br.com.dev.catalog.dto.ProductDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDto createProductDTO() {
        Product product = createProduct();
        return new ProductDto(product, product.getCategories());
    }
}
