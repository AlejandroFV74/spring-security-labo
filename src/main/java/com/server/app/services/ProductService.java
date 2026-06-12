package com.server.app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.server.app.entities.*;
import com.server.app.repositories.*;
import com.server.app.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product create(Product product, Integer catalogId, Integer categoryId, User currentUser) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        product.setCatalog(catalog);
        product.setCategory(category);
        product.setCreatedBy(currentUser); //usuario con el auth

        return productRepository.save(product);
    }

    @Transactional
    public Product update(Integer id, Product details, Integer catalogId, Integer categoryId) {
        Product product = findById(id);

        if (catalogId != null) {
            Catalog catalog = catalogRepository.findById(catalogId)
                    .orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));
            product.setCatalog(catalog);
        }
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }

        product.setName(details.getName());
        product.setPrice(details.getPrice());

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Integer id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public Page<Product> findAll(int page, int size, String search) {
        return productRepository.findAllPaged(search, PageRequest.of(page, size));
    }

    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
}
