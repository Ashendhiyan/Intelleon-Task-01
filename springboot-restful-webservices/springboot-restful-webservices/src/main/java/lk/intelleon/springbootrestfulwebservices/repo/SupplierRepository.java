package lk.intelleon.springbootrestfulwebservices.repo;

import lk.intelleon.springbootrestfulwebservices.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
}
