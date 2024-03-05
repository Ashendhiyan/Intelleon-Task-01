package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.InventoryDTO;
import lk.intelleon.springbootrestfulwebservices.entity.InventoryEntity;
import lk.intelleon.springbootrestfulwebservices.repo.InventoryRepository;
import lk.intelleon.springbootrestfulwebservices.service.InventoryService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository repository;

    @Autowired
    Convertor convertor;

    @Override
    public void saveInventory(InventoryDTO inventoryDTO) {
        repository.save(convertor.InventoryDtoToInventoryEntity(inventoryDTO));
    }

    @Override
    public void updateInventory(InventoryDTO inventoryDTO) {
        InventoryEntity inventoryEntity = repository.findById(inventoryDTO.getId()).get();
        // Update the attributes of the InventoryEntity using InventoryDTO
        inventoryEntity.setReceivedDate(inventoryDTO.getReceivedDate());
        inventoryEntity.setReceivedQty(inventoryDTO.getReceivedQty());
        inventoryEntity.setApprovalStatus(inventoryDTO.getApprovalStatus());
        inventoryEntity.setStatus(inventoryDTO.getStatus());
        // Assuming item not updated in this method
        repository.save(inventoryEntity);
    }

    @Override
    public void deleteInventory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        List<InventoryEntity> all = repository.findAll();
        return convertor.InventoryEntityListToInventoryDTOList(all);
    }
}
