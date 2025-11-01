package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import com.debuggeando_ideas.best_travel_2025.domain.entities.*;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel_2025.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.debuggeando_ideas.best_travel_2025.infrastructure.Services.ReservationService.charges_price_percentage;
import static com.debuggeando_ideas.best_travel_2025.infrastructure.Services.TicketService.charger_price_percentage;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    private Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        var response = new HashSet<TicketEntity>(flights.size());

        for (FlyEntity fly : flights) {
            var ticketToPersist  = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .arrivalDate(BestTravelUtil.getRandomLatter())
                    .purchaseDate(LocalDate.now())
                    .fly(fly)
                    .customer(customer)
                    .build();
            response.add(this.ticketRepository.save(ticketToPersist));
        }
        return response;
    }

    private Set<ReservationEntity> createReservations(HashMap<HotelEntity,Integer> hotels, CustomerEntity customer) {
        var response = new HashSet<ReservationEntity>(hotels.size());

        hotels.forEach((hotel, totalDays)->{
            var reservationToPersist  = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                    .hotel(hotel)
                    .customer(customer)
                    .build();
            response.add(this.reservationRepository.save(reservationToPersist));
        });
        return response;
    }
}
