package br.com.dev.catalog.service;

import br.com.dev.catalog.dto.CategoryDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.exceptions.DatabaseException;
import br.com.dev.catalog.exceptions.ResourceNotFoundException;
import br.com.dev.catalog.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAll(String name, Pageable pageable) {

        Page<Category> result = repository.searchByName(name, pageable);

        return result.map(category -> mapper.map(category, CategoryDto.class));
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return mapper.map(category, CategoryDto.class);
    }

    @Transactional
    public CategoryDto insert(CategoryDto dto) {

     Category category = mapper.map(dto, Category.class);

     return mapper.map(category, CategoryDto.class);

    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {

        try {

            Category entity = repository.getReferenceById(id);

            mapper.map(dto, entity);

            entity = repository.save(entity);

            return mapper.map(entity, CategoryDto.class);

        } catch (EntityNotFoundException e) {

            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado com ID: " + id);
        }
        try {
            repository.deleteById(id);

        } catch (DataIntegrityViolationException e) {

            throw new DatabaseException("Falha de integridade referencial ao excluir o recurso com ID: " + id);
        }
    }
}