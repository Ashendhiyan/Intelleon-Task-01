package lk.intelleon.springbootrestfulwebservices.restController;

import lk.intelleon.springbootrestfulwebservices.dto.CategoryDTO;
import lk.intelleon.springbootrestfulwebservices.dto.ItemDTO;
import lk.intelleon.springbootrestfulwebservices.entity.CategoryEntity;
import lk.intelleon.springbootrestfulwebservices.entity.ItemEntity;
import lk.intelleon.springbootrestfulwebservices.entity.UnitEntity;
import lk.intelleon.springbootrestfulwebservices.service.CategoryService;
import lk.intelleon.springbootrestfulwebservices.service.ItemService;
import lk.intelleon.springbootrestfulwebservices.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
public class ItemRestController {
    @Autowired
    ItemService itemService;

    @Autowired
    CategoryService categoryService; // Inject CategoryService

    @Autowired
    UnitService unitService; // Inject UnitService


    @PostMapping
    public ResponseEntity<String> saveItem(@RequestBody ItemDTO itemDTO) {
        // Map CategoryDTO to CategoryEntity
        CategoryEntity categoryEntity = categoryService.getCategoryById(itemDTO.getCategoryId());
        // Map UnitDTO to UnitEntity
        UnitEntity unitEntity = unitService.getUnitById(itemDTO.getUnitId());

        // Create ItemEntity and set mapped CategoryEntity and UnitEntity
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCode(itemDTO.getCode());
        itemEntity.setName(itemDTO.getName());
        itemEntity.setCategory(categoryEntity);
        itemEntity.setUnit(unitEntity);
        itemEntity.setStatus(itemDTO.getStatus());
        itemService.saveItem(itemDTO);
        return new ResponseEntity<>(" Item is saved..!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") Long id, @RequestBody ItemDTO itemDTO) {
        itemDTO.setId(id);
        itemService.updateItem(itemDTO);
        return new ResponseEntity<>("Item is updated..!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item successfully deleted..!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }
}
