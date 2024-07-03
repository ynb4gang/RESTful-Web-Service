package smth.moySklad.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smth.moySklad.model.Supply;
import smth.moySklad.service.SupplyService;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @GetMapping
    public List<Supply> getAllSupplies() {
        return supplyService.getAllSupplies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        return ResponseEntity.ok(supplyService.getSupplyById(id));
    }

    @PostMapping
    public Supply createSupply(@Valid @RequestBody Supply supply) {
        return supplyService.createSupply(supply);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @Valid @RequestBody Supply supplyDetails) {
        return ResponseEntity.ok(supplyService.updateSupply(id, supplyDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}
