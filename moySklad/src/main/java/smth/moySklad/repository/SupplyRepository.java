package smth.moySklad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smth.moySklad.model.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
