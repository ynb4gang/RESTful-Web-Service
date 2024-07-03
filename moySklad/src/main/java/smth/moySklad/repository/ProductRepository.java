package smth.moySklad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smth.moySklad.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
