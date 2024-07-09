package ru.elias.external.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.elias.external.domain.entity.DealerWarehouseEntity;

public interface DealerWarehouseRepository extends JpaRepository<DealerWarehouseEntity, UUID> {
}
