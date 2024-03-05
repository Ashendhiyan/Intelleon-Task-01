package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.entity.ItemEntity;
import lk.intelleon.springbootrestfulwebservices.repo.ItemRepository;
import lk.intelleon.springbootrestfulwebservices.service.ItemService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository repository;

    @Autowired
    Convertor convertor;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        repository.save(convertor.ItemDtoToItemEntity(itemDTO));
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        ItemEntity itemEntity = repository.findById(itemDTO.getId()).get();
        // Update the attributes of the itemEntity using itemDTO
        itemEntity.setCode(itemDTO.getCode());
        itemEntity.setName(itemDTO.getName());
        // Assuming category and unit are not updated in this method
        itemEntity.setStatus(itemDTO.getStatus());
        repository.save(itemEntity);
    }

    @Override
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<ItemEntity> all = repository.findAll();
        return convertor.ItemEntityListToItemDTOList(all);

    /*    List<ItemEntity> itemEntities = repository.findAll();
        return itemEntities.stream()
                .map(convertor::ItemEntityToItemDto)
                .collect(Collectors.toList());*/
    }
}
