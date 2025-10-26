package com.debuggeando_ideas.best_travel_2025.domain.repositories;

import com.debuggeando_ideas.best_travel_2025.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface FlyRepository extends JpaRepository<FlyEntity, Long> {
    Set<FlyEntity> findByPriceLessThan(BigDecimal price);
    Set<FlyEntity> findByPriceBetween(BigDecimal min, BigDecimal max);
    Set<FlyEntity> findByOriginNameIgnoreCaseAndDestinyNameIgnoreCase(String originName, String destinyName);
    Optional<FlyEntity>findByTicketsId(UUID ticketId);
}
