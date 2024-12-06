package br.com.dev.catalog.repository;

import br.com.dev.catalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT obj FROM Category obj " +
            "where UPPER (obj.name) LIKE  UPPER(CONCAT('%', :name, '%'))")
    Page<Category> searchByName(String name, Pageable pageable);

}
