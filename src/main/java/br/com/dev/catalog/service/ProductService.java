package br.com.dev.catalog.service;

import br.com.dev.catalog.dto.ProductDto;
import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.entities.Product;
import br.com.dev.catalog.exceptions.DatabaseException;
import br.com.dev.catalog.exceptions.ResourceNotFoundException;
import br.com.dev.catalog.repository.ProductRepository;
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
public class ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(String name, Pageable pageable) {

        Page<Category> result = repository.searchByName(name, pageable);

        return result.map(category -> mapper.map(category, ProductDto.class));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        return new ProductDto(product, product.getCategories());
    }

    @Transactional
    public ProductDto insert(ProductDto dto) {


     Product product = mapper.map(dto, Product.class);

     product = repository.save(product);

     return mapper.map(product, ProductDto.class);

    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {

        try {

            Product entity = repository.getReferenceById(id);

            mapper.map(dto, entity);

            entity = repository.save(entity);

            return mapper.map(entity, ProductDto.class);

        } catch (EntityNotFoundException e) {

            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        try {
            repository.deleteById(id);

        } catch (DataIntegrityViolationException e) {

            throw new DatabaseException("Integrity violation");
        }
    }



}