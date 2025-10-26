package com.debuggeando_ideas.best_travel_2025.domain.repositories;

import com.debuggeando_ideas.best_travel_2025.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
}
