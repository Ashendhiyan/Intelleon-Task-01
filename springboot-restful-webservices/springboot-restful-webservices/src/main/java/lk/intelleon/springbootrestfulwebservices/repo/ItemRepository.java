package lk.intelleon.springbootrestfulwebservices.repo;

import lk.intelleon.springbootrestfulwebservices.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
}
