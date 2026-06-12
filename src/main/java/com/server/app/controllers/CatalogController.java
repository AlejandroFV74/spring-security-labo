package com.server.app.controllers;

import com.server.app.entities.Catalog;
import com.server.app.services.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/catalogos")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<List<Catalog>> getAll() {
        return ResponseEntity.ok(catalogService.findAll());
    }

    @PostMapping
    public ResponseEntity<Catalog> create(@RequestBody Catalog catalog) {
        return ResponseEntity.ok(catalogService.create(catalog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Catalog> update(@PathVariable Integer id, @RequestBody Catalog catalogDetails) {
        return ResponseEntity.ok(catalogService.update(id, catalogDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        catalogService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Catálogo eliminado"));
    }
}