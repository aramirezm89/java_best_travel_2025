package com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest,ReservationResponse, UUID>{
    BigDecimal findPrice(Long hotelId, String currency);
}
