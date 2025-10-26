package com.debuggeando_ideas.best_travel_2025;

import com.debuggeando_ideas.best_travel_2025.domain.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class BestTravel2025Application implements CommandLineRunner {


    private final  FlyRepository flyRepository;

    private final  HotelRepository hotelRepository;

    private final TicketRepository ticketRepository;

    private final ReservationRepository reservationRepository;

    private final TourRepository tourRepository;

    private final CustomerRepository customerRepository;

    public BestTravel2025Application(FlyRepository flyRepository, HotelRepository hotelRepository, TicketRepository ticketRepository, ReservationRepository reservationRepository, TourRepository tourRepository, CustomerRepository customerRepository) {
        this.flyRepository = flyRepository;
        this.hotelRepository = hotelRepository;
        this.ticketRepository = ticketRepository;
        this.reservationRepository = reservationRepository;
        this.tourRepository = tourRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BestTravel2025Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    /*    System.out.println("Best Travel 2025");
        var hoteles = hotelRepository.findById(7L).get();
        var vuelos = flyRepository.findById(1L).get();
        var ticket = ticketRepository.findById(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
        var reservation = reservationRepository.findById(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
        var customer = customerRepository.findById("VIKI771012HMCRG093").get();
        var vuelosMenorPrecio = flyRepository.findByPriceLessThan(new BigDecimal(20));
        var vuelosEntrePrecios = flyRepository.findByPriceBetween(new BigDecimal(20), new BigDecimal(50));
        var vuelosPorOrigenDestino = flyRepository.findByOriginNameIgnoreCaseAndDestinyNameIgnoreCase("mexico", "grecia");

        var vueloYtickets = vuelos.getTickets();

        var vueloPorTicketId = flyRepository.findByTicketsId(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
       log.info("Hoteles: {}", hoteles);
       log.info("Vuelos: {}", vuelos);
       log.info("Tickets: {}", ticket);
       log.info("Reservations: {}", reservation);
       log.info("Customers: {}", customer);
       log.info("Vuelos menor precio: {}", vuelosMenorPrecio);
       log.info("Vuelos entre precios: {}", vuelosEntrePrecios);
       log.info("Vuelos por origen y destino: {}", vuelosPorOrigenDestino);
       log.info("Vuelos y tickets: {}", vueloYtickets);

        var hotelesMenorPrecio = hotelRepository.findByPriceLessThan(new BigDecimal(20));
        var hotelesEntrePrecios = hotelRepository.findByPriceBetween(new BigDecimal(20), new BigDecimal(50));
        var hotelesPorNombre = hotelRepository.findByName("El dorado");
        var hotelesPorRating = hotelRepository.findByRatingGreaterThan(2);
        var hotelesPorReserva = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();

        log.info("Hoteles menor precio: {}", hotelesMenorPrecio);
        log.info("Hoteles entre precios: {}", hotelesEntrePrecios);
        log.info("Hoteles por nombre: {}", hotelesPorNombre);
        log.info("Hoteles por rating: {}", hotelesPorRating);
        log.info("Hoteles por reserva: {}", hotelesPorReserva);*/
    }
}
