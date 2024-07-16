package ru.elias.config.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.elias.config.domain.entity.ConfigEntity;

public interface ConfigRepository extends JpaRepository<ConfigEntity, UUID> {
}
