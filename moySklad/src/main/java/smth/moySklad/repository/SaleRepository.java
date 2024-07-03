package smth.moySklad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smth.moySklad.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
