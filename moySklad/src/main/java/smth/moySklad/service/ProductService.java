package smth.moySklad.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smth.moySklad.model.Product;
import smth.moySklad.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return  productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setAvailable(productDetails.isAvailable());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProducts(String filterByName, BigDecimal minPrice, BigDecimal maxPrice, Boolean available, String sortBy, String sortOrder, Integer limit) {
        List<Product> products = productRepository.findAll();

        if (filterByName != null && !filterByName.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(filterByName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (minPrice != null) {
            products = products.stream()
                    .filter(product -> product.getPrice().compareTo(minPrice.doubleValue()) >= 0)
                    .collect(Collectors.toList());
        }

        if (maxPrice != null) {
            products = products.stream()
                    .filter(product -> product.getPrice().compareTo(maxPrice.doubleValue()) <= 0)
                    .collect(Collectors.toList());
        }

        if (available != null) {
            products = products.stream()
                    .filter(product -> product.isAvailable() == available)
                    .collect(Collectors.toList());
        }

        if (sortBy.equals("name")) {
            products.sort((p1, p2) -> sortOrder.equals("asc") ? p1.getName().compareTo(p2.getName()) : p2.getName().compareTo(p1.getName()));
        } else if (sortBy.equals("price")) {
            products.sort((p1, p2) -> sortOrder.equals("asc") ? p1.getPrice().compareTo(p2.getPrice()) : p2.getPrice().compareTo(p1.getPrice()));
        }

        if (limit != null && limit > 0) {
            products = products.stream().limit(limit).collect(Collectors.toList());
        }

        return products;
    }
}
