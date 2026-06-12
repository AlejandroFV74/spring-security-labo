package com.server.app.services;

import com.server.app.entities.Catalog;
import com.server.app.repositories.CatalogRepository;
import com.server.app.services.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    @Transactional(readOnly = true)
    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Catalog findById(Integer id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catálogo no encontrado con id: " + id));
    }

    @Transactional
    public Catalog create(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Transactional
    public Catalog update(Integer id, Catalog catalogDetails) {
        Catalog catalog = findById(id);
        catalog.setName(catalogDetails.getName());
        catalog.setDescription(catalogDetails.getDescription());
        return catalogRepository.save(catalog);
    }

    @Transactional
    public void delete(Integer id) {
        Catalog catalog = findById(id);
        catalogRepository.delete(catalog);
    }
}