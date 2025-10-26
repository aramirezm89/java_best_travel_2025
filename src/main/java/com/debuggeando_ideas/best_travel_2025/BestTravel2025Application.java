package com.debuggeando_ideas.best_travel_2025;

import com.debuggeando_ideas.best_travel_2025.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BestTravel2025Application implements CommandLineRunner {

    @Autowired
    private  FlyRepository flyRepository;
    @Autowired
    private  HotelRepository hotelRepository;
    public static void main(String[] args) {
        SpringApplication.run(BestTravel2025Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Best Travel 2025");
        var hoteles = hotelRepository.findById(1L);
        var vuelos = flyRepository.findById(1L);
       log.info("Hoteles: {}", hoteles);
       log.info("Vuelos: {}", vuelos);
    }
}
