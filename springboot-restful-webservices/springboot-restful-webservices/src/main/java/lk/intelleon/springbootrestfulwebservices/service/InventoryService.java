package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.InventoryDTO;
import lk.intelleon.springbootrestfulwebservices.entity.CategoryEntity;

import java.util.List;

public interface InventoryService {
    void saveInventory(InventoryDTO inventoryDTO);
    void updateInventory(InventoryDTO inventoryDTO);
    void deleteInventory(Long id);
    List<InventoryDTO> getAllInventory();

}
