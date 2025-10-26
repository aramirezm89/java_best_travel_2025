package com.debuggeando_ideas.best_travel_2025.domain.repositories;

import com.debuggeando_ideas.best_travel_2025.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel_2025.domain.entities.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
}
