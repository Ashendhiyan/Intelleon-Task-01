package lk.intelleon.springbootrestfulwebservices.restController;

import lk.intelleon.springbootrestfulwebservices.dto.InventoryDTO;
import lk.intelleon.springbootrestfulwebservices.entity.InventoryEntity;
import lk.intelleon.springbootrestfulwebservices.entity.ItemEntity;
import lk.intelleon.springbootrestfulwebservices.service.InventoryService;
import lk.intelleon.springbootrestfulwebservices.service.ItemService;
import lk.intelleon.springbootrestfulwebservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/inventory")
public class InventoryRestController {
    @Autowired
    InventoryService inventoryService;

    @Autowired
    ItemService itemService;    // Inject ItemService

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> saveInventory(@RequestBody InventoryDTO inventoryDTO) {
        // Map InventoryDTO to InventoryEntity
        ItemEntity itemEntity = itemService.getItemById(inventoryDTO.getItemId());
        // Create InventoryEntity and set mapped InventoryEntity
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setItem(itemEntity);
        inventoryEntity.setReceivedDate(inventoryDTO.getReceivedDate());
        inventoryEntity.setExpireDate(inventoryDTO.getExpireDate());
        inventoryEntity.setReceivedQty(inventoryDTO.getReceivedQty());
        inventoryEntity.setApprovalStatus(inventoryDTO.getApprovalStatus());
        inventoryEntity.setStatus(inventoryDTO.getStatus());
        inventoryService.saveInventory(inventoryDTO);
        return new ResponseEntity<>("Inventory is saved!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateInventory(@PathVariable("id") Long id, @RequestBody InventoryDTO inventoryDTO) {
        inventoryDTO.setId(id);
        inventoryService.updateInventory(inventoryDTO);
        return new ResponseEntity<>("Inventory is updated!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") Long id) {
        inventoryService.deleteInventory(id);
        return new ResponseEntity<>("Inventory successfully deleted!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventories = inventoryService.getAllInventory();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

}
