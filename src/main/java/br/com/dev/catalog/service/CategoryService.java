package br.com.dev.catalog.service;

import br.com.dev.catalog.entities.Category;
import br.com.dev.catalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {

        return repository.findAll();
    }
};
