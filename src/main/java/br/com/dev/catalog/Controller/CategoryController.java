package br.com.dev.catalog.Controller;

import br.com.dev.catalog.dto.CategoryDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity <Page<CategoryDto>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
        return ResponseEntity.ok(service.findAll(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
