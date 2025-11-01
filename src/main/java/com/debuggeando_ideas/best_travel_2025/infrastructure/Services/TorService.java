package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TourResponse;
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

import java.util.UUID;

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
        return null;
    }

    @Override
    public TourResponse read(Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
