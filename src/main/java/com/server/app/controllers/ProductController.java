package com.server.app.controllers;

import com.server.app.entities.Product;
import com.server.app.entities.User;
import com.server.app.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> payload, @AuthenticationPrincipal User currentUser) {
        Product product = new Product();
        product.setName((String) payload.get("name"));
        product.setPrice(Double.parseDouble(payload.get("price").toString()));

        Integer catalogId = (Integer) payload.get("catalogId");
        Integer categoryId = (Integer) payload.get("categoryId");

        Product created = productService.create(product, catalogId, categoryId, currentUser);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        Product productDetails = new Product();
        productDetails.setName((String) payload.get("name"));
        productDetails.setPrice(Double.parseDouble(payload.get("price").toString()));

        Integer catalogId = (Integer) payload.get("catalogId");
        Integer categoryId = (Integer) payload.get("categoryId");

        Product updated = productService.update(id, productDetails, catalogId, categoryId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Producto eliminado exitosamente"));
    }
    
    @GetMapping
    public ResponseEntity<Page<Product>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search) {
        return ResponseEntity.ok(productService.findAll(page, size, search));
    }
}