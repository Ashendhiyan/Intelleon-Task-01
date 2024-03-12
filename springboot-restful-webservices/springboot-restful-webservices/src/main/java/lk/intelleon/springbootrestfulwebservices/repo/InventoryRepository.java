package lk.intelleon.springbootrestfulwebservices.repo;

import lk.intelleon.springbootrestfulwebservices.entity.InventoryEntity;
import lk.intelleon.springbootrestfulwebservices.projection.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {

}
