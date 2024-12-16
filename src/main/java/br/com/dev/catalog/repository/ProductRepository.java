package br.com.dev.catalog.repository;

import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT obj FROM Product obj " +
            "where UPPER (obj.name) LIKE  UPPER(CONCAT('%', :name, '%'))")
    Page<Category> searchByName(String name, Pageable pageable);

}
