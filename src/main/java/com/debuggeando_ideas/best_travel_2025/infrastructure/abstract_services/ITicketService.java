package com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID> {

    BigDecimal findPrice(Long flyId);
}
