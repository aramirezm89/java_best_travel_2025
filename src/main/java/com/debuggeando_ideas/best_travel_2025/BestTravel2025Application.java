package com.debuggeando_ideas.best_travel_2025;

import com.debuggeando_ideas.best_travel_2025.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TourEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravel2025Application {
    public static void main(String[] args) {
        SpringApplication.run(BestTravel2025Application.class, args);
    }
}
