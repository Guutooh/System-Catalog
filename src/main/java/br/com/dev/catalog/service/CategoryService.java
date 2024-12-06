package br.com.dev.catalog.service;

import br.com.dev.catalog.dto.CategoryDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll (String name, Pageable pageable) {

        Page<Category> result = repository.searchByName(name, pageable);

        return result.map(category -> mapper.map(category, CategoryDto.class));
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        return mapper.map(category, CategoryDto.class);
    }



}
