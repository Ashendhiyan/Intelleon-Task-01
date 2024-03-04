package lk.intelleon.springbootrestfulwebservices.restController;

import lk.intelleon.springbootrestfulwebservices.dto.UnitDTO;
import lk.intelleon.springbootrestfulwebservices.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/unit")
public class UnitRestController {

    @Autowired
    UnitService unitService;

    @PostMapping
    public ResponseEntity<String> saveUnit(@RequestBody UnitDTO unitDTO) {
        unitService.saveUnit(unitDTO);
        return new ResponseEntity<>(" Unit is saved..!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUnit(@PathVariable("id") Long id, @RequestBody UnitDTO unitDTO) {
        unitDTO.setId(id);
        unitService.updateUnit(unitDTO);
        return new ResponseEntity<>("Unit is updated..!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUnit(@PathVariable("id") Long id) {
        unitService.deleteUnit(id);
        return new ResponseEntity<>("Unit successfully deleted..!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
        return new ResponseEntity<>(unitService.getAllUnit(), HttpStatus.OK);
    }
}

