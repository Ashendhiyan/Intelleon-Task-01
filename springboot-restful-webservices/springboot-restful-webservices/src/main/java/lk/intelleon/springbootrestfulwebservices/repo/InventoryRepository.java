package lk.intelleon.springbootrestfulwebservices.repo;

import lk.intelleon.springbootrestfulwebservices.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {
}
