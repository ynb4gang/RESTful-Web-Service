package smth.moySklad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smth.moySklad.exception.ResourceNotFoundException;
import smth.moySklad.model.Supply;
import smth.moySklad.repository.SupplyRepository;

import java.util.List;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply", "id", id));
    }

    public Supply createSupply(Supply supply) {
        return supplyRepository.save(supply);
    }

    public Supply updateSupply(Long id, Supply supplyDetails) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply", "id", id));

        supply.setDocumentName(supplyDetails.getDocumentName());
        supply.setProduct(supplyDetails.getProduct());
        supply.setQuantity(supplyDetails.getQuantity());

        return supplyRepository.save(supply);
    }

    public void deleteSupply(Long id) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply", "id", id));
        supplyRepository.delete(supply);
    }
}
