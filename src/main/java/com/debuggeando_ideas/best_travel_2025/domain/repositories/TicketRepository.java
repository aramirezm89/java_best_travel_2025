package com.debuggeando_ideas.best_travel_2025.domain.repositories;

import com.debuggeando_ideas.best_travel_2025.domain.entities.CustomerEntity;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
}
