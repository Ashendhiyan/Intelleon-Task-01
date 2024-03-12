package lk.intelleon.springbootrestfulwebservices.util;

import lk.intelleon.springbootrestfulwebservices.dto.*;
import lk.intelleon.springbootrestfulwebservices.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Convertor {
    @Autowired
    ModelMapper modelMapper;

    //Supplier
    public SupplierEntity supplierDtoToSupplierEntity(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, SupplierEntity.class);
    }
    public SupplierDTO supplierEntityToSupplierDto(SupplierEntity supplier) {
        return modelMapper.map(supplier,SupplierDTO.class);
    }
    public List<SupplierDTO> supplierEntityListToSupplierDTOList(List<SupplierEntity> suppliers){
        return modelMapper.map(suppliers,new TypeToken<List<SupplierDTO>>(){}.getType());
    }

    //Unit
    public UnitEntity unitDtoTounitEntity(UnitDTO unitDTO) {
        return modelMapper.map(unitDTO, UnitEntity.class);
    }
    public UnitDTO unitEntityToUnitDto(UnitEntity unit) {
        return modelMapper.map(unit,UnitDTO.class);
    }
    public List<UnitDTO> unitEntityListTounitDTOList(List<UnitEntity> unit){
        return modelMapper.map(unit,new TypeToken<List<UnitDTO>>(){}.getType());
    }


    //Category
    public CategoryEntity CategoryDtoToCategoryEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, CategoryEntity.class);
    }
    public CategoryDTO CategoryEntityToCategoryDto(CategoryEntity entity) {
        return modelMapper.map(entity,CategoryDTO.class);
    }
    public List<CategoryDTO> CategoryEntityListToCategoryDTOList(List<CategoryEntity> category){
        return modelMapper.map(category,new TypeToken<List<CategoryDTO>>(){}.getType());
    }


    //Item
    public ItemEntity ItemDtoToItemEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, ItemEntity.class);
    }
    public ItemDTO ItemEntityToItemDto(ItemEntity itemEntity) {
        return modelMapper.map(itemEntity,ItemDTO.class);
    }
    public List<ItemDTO> ItemEntityListToItemDTOList(List<ItemEntity> items){
        return modelMapper.map(items,new TypeToken<List<ItemDTO>>(){}.getType());
    }

    //Inventory
    public InventoryEntity InventoryDtoToInventoryEntity(InventoryDTO inventoryDTO) {
        return modelMapper.map(inventoryDTO, InventoryEntity.class);
    }
    public InventoryDTO InventoryEntityToInventoryDto(InventoryEntity inventoryEntity) {
        return modelMapper.map(inventoryEntity,InventoryDTO.class);
    }
    public List<InventoryDTO> InventoryEntityListToInventoryDTOList(List<InventoryEntity> inventoryEntityList){
        return modelMapper.map(inventoryEntityList,new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    //User
    public UserEntity UserDtoToUserEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }

    public List<UserDTO> UserEntityListToUserDTOList(List<UserEntity> userEntityList){
        return modelMapper.map(userEntityList,new TypeToken<List<UserDTO>>(){}.getType());
    }
}
