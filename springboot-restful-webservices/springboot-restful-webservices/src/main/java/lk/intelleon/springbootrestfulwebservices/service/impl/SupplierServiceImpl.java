package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.SupplierDTO;
import lk.intelleon.springbootrestfulwebservices.entity.SupplierEntity;
import lk.intelleon.springbootrestfulwebservices.repo.SupplierRepository;
import lk.intelleon.springbootrestfulwebservices.service.SupplierService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository repository;

    @Autowired
    Convertor convertor;

    @Override
    public void saveSupplier(SupplierDTO supplier) {
    repository.save(convertor.supplierDtoToSupplierEntity(supplier));
    }

    @Override
    public void updateSupplier(SupplierDTO supplier) {
        SupplierEntity supplierEntity = repository.findById(supplier.getId()).get();
        supplierEntity.setSupplierCode(supplier.getSupplierCode());
        supplierEntity.setName(supplier.getName());
        supplierEntity.setAddress(supplier.getAddress());
        supplierEntity.setStatus(supplier.getStatus());
        repository.save(supplierEntity);
    }


    @Override
    public void deleteSupplier(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<SupplierEntity> all = repository.findAll();
        return convertor.supplierEntityListToSupplierDTOList(all);
    }

}
