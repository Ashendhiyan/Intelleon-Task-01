package lk.intelleon.springbootrestfulwebservices.service;

import lk.intelleon.springbootrestfulwebservices.dto.SupplierDTO;
import lk.intelleon.springbootrestfulwebservices.dto.UnitDTO;

import java.util.List;

public interface UnitService {
    void saveUnit(UnitDTO unitDTO);
    void updateUnit(UnitDTO unitDTO);
    void deleteUnit(Long id);
    List<UnitDTO> getAllUnit();
}
