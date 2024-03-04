package lk.intelleon.springbootrestfulwebservices.service.impl;

import lk.intelleon.springbootrestfulwebservices.dto.UnitDTO;
import lk.intelleon.springbootrestfulwebservices.entity.SupplierEntity;
import lk.intelleon.springbootrestfulwebservices.entity.UnitEntity;
import lk.intelleon.springbootrestfulwebservices.repo.UnitRepository;
import lk.intelleon.springbootrestfulwebservices.service.UnitService;
import lk.intelleon.springbootrestfulwebservices.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository repository;

    @Autowired
    Convertor convertor;

    @Override
    public void saveUnit(UnitDTO unitDTO) {
        repository.save(convertor.unitDtoTounitEntity(unitDTO));
    }

    @Override
    public void updateUnit(UnitDTO unitDTO) {
        UnitEntity unitEntity = repository.findById(unitDTO.getId()).get();
        unitEntity.setCode(unitDTO.getCode());
        unitEntity.setName(unitDTO.getName());
        unitEntity.setStatus(unitDTO.getStatus());
        repository.save(unitEntity);
    }

    @Override
    public void deleteUnit(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UnitDTO> getAllUnit() {
        List<UnitEntity> all = repository.findAll();
        return convertor.unitEntityListTounitDTOList(all);
    }
}
