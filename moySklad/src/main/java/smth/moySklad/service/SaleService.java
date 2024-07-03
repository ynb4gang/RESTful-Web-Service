package smth.moySklad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smth.moySklad.exception.ResourceNotFoundException;
import smth.moySklad.model.Sale;
import smth.moySklad.repository.SaleRepository;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
    }

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale saleDetails) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));

        sale.setDocumentName(saleDetails.getDocumentName());
        sale.setProduct(saleDetails.getProduct());
        sale.setQuantity(saleDetails.getQuantity());
        sale.setPurchaseCost(saleDetails.getPurchaseCost());

        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
        saleRepository.delete(sale);
    }
}
