package br.com.dev.catalog.service;

import br.com.dev.catalog.dto.CategoryDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CategoryDto> findAll() {

        List<Category> result = repository.findAll();

        return result.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
    }
}
