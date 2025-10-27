package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITicketService;
import com.debuggeando_ideas.best_travel_2025.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {
        var fly = this.flyRepository.findById(request.getFlyId()).orElseThrow();
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow();
        var ticketToPersist  = TicketEntity.builder()
                .id(UUID.randomUUID())
                .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
                .departureDate(BestTravelUtil.getRandomSoon())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .purchaseDate(LocalDate.now())
                .fly(fly)
                .customer(customer)
                .build();
         var ticketPersisted = this.ticketRepository.save(ticketToPersist);
         log.info("Ticket persisted: {}", ticketPersisted);
         return this.toTicketResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticket = this.ticketRepository.findById(uuid).orElseThrow();
        return this.toTicketResponse(ticket);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = this.ticketRepository.findById(uuid).orElseThrow();
        var fly = this.flyRepository.findById(request.getFlyId()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
        log.info("Ticket updated: {}", ticketUpdated.getId());
        return this.toTicketResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
        log.info("Ticket deleted: {}", ticketToDelete.getId());
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = this.flyRepository.findById(flyId).orElseThrow();
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
    }

    private TicketResponse toTicketResponse(TicketEntity ticketEntity) {
       var response = new TicketResponse();
        BeanUtils.copyProperties(ticketEntity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(ticketEntity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }


    private static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}
