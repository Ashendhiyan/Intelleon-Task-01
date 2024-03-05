package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.entity.ItemEntity;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);

    void deleteItem(Long id);

    List<ItemDTO> getAllItems();

    ItemEntity getItemById(Long itemId);
}
