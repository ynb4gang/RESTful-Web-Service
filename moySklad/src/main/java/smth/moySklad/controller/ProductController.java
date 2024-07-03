package smth.moySklad.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smth.moySklad.model.Product;
import smth.moySklad.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(name = "filterByName", required = false) String filterByName,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "available", required = false) Boolean available,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "limit", required = false) Integer limit
    ) {
        validateParams(minPrice, maxPrice, sortBy, sortOrder, limit);

        List<Product> products = productService.getProducts(filterByName, minPrice, maxPrice, available, sortBy, sortOrder, limit);

        return ResponseEntity.ok(products);
    }
    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllproducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return  productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct (@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private void validateParams(BigDecimal minPrice, BigDecimal maxPrice, String sortBy, String sortOrder, Integer limit) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }
        if (!sortBy.equals("name") && !sortBy.equals("price")) {
            throw new IllegalArgumentException("Invalid sortBy parameter. Should be 'name' or 'price'.");
        }
        if (!sortOrder.equals("asc") && !sortOrder.equals("desc")) {
            throw new IllegalArgumentException("Invalid sortOrder parameter. Should be 'asc' or 'desc'.");
        }
        if (limit != null && limit <= 0) {
            throw new IllegalArgumentException("Limit should be a positive number.");
        }
    }
}
