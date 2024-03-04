package lk.intelleon.springbootrestfulwebservices.restController;

import lk.intelleon.springbootrestfulwebservices.dto.SupplierDTO;
import lk.intelleon.springbootrestfulwebservices.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping
    public ResponseEntity<String> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        supplierService.saveSupplier(supplierDTO);
        return new ResponseEntity<>(" Supplier is saved..!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateSupplier(@PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setId(id);
        supplierService.updateSupplier(supplierDTO);
        return new ResponseEntity<>("Supplier is updated..!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>("Supplier successfully deleted..!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return new ResponseEntity<>(supplierService.getAllSuppliers(), HttpStatus.OK);
    }
}
