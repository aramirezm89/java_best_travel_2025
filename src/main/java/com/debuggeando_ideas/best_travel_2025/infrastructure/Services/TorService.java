package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TourResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.*;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.TourRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITourService;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.TourHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TorService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;

    @Override
    public void removeTicket(Long tourId, UUID ticketId) {

    }

    @Override
    public UUID addTicket(Long tourId, Long flyId) {
        return null;
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {

    }

    @Override
    public UUID addReservation(Long tourId, Long reservationId) {
        return null;
    }

    @Override
    public TourResponse create(TourRequest request) {

        //obtengo el cliente
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow();
        //obtengo un listado de todos los vuelos segun la id que llega en la request
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(flight -> this.flyRepository.findById(flight.getId()).orElseThrow());
        //inserto los tickets en la base de datos con los vuelos y el cliente
        var tickets = this.tourHelper.createTickets(flights, customer);

        //obtengo un listado de todos los hoteles segun la id que llega en la request
        var hotels= new HashMap<HotelEntity,Integer>();
        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(),hotel.getTotalDays()));
        //inserto las reservaciones en la base de datos con los hoteles y el cliente
        var reservations = this.tourHelper.createReservations(hotels, customer);

        var tourToPersist = TourEntity.builder()
                .customer(customer)
                .tickets(tickets)
                .reservations(reservations)
                .build();
        var tourPersisted = this.tourRepository.save(tourToPersist);
        return this.tourResponse(tourPersisted);
    }

    @Override
    public TourResponse read(Long id) {

        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    private TourResponse tourResponse(TourEntity tourEntity) {
       return TourResponse.builder()
                .id(tourEntity.getId())
                .ticketIds(tourEntity.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .reservationIds(tourEntity.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .build();
    }
}
