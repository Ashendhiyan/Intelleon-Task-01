package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);
    void updateItem(ItemDTO itemDTO);
    void deleteItem(Long id);
    List<ItemDTO> getAllItems();
}
