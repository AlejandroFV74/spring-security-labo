package com.server.app.services;

import com.server.app.entities.Category;
import com.server.app.repositories.CategoryRepository;
import com.server.app.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Integer id, Category categoryDetails) {
        Category category = findById(id);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Integer id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}