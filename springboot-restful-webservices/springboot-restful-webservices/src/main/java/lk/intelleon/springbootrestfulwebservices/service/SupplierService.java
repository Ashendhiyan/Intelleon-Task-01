package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.SupplierDTO;
import lk.intelleon.springbootrestfulwebservices.entity.SupplierEntity;

import java.util.List;
import java.util.UUID;

public interface SupplierService {
    void saveSupplier(SupplierDTO supplier);
    void updateSupplier(SupplierDTO supplier);
    void deleteSupplier(Long id);
    List<SupplierDTO> getAllSuppliers();

}
