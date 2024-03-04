package lk.intelleon.springbootrestfulwebservices.repo;

import lk.intelleon.springbootrestfulwebservices.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitEntity,Long> {
}
