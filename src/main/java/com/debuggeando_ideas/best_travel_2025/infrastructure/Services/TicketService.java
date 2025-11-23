package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.TicketRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITicketService;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.ApiCurrencyConectorHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.BlackListHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.CustomerHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.EmailHelper;
import com.debuggeando_ideas.best_travel_2025.util.BestTravelUtil;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.IdNotFoundException;
import com.debuggeando_ideas.best_travel_2025.util.enums.Tables;
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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;
    private final ApiCurrencyConectorHelper apiCurrencyConectorHelper;
    @Override
    public TicketResponse create(TicketRequest request) {
        this.blackListHelper.isBlackListed(request.getCustomerId());
        var fly = this.flyRepository.findById(request.getFlyId()).orElseThrow(() -> new IdNotFoundException(Tables.FLY.name()));
        var customer = this.customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IdNotFoundException(Tables.CUSTOMER.name()));
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
         this.customerHelper.increase(customer.getDni(), TicketService.class);

        if(request.getEmail() != null){
            this.emailHelper.sendEmail(request.getEmail(), customer.getFullName(), Tables.TICKET.name());
        }
         return this.toTicketResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticket = this.ticketRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.TICKET.name()));
        return this.toTicketResponse(ticket);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        var ticketToUpdate = this.ticketRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.TICKET.name()));
        var fly = this.flyRepository.findById(request.getFlyId()).orElseThrow(() -> new IdNotFoundException(Tables.FLY.name()));

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
        var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.TICKET.name()));
        this.ticketRepository.delete(ticketToDelete);
        log.info("Ticket deleted: {}", ticketToDelete.getId());
    }

    @Override
    public BigDecimal findPrice(Long flyId, String currency) {
        var fly = this.flyRepository.findById(flyId).orElseThrow(() -> new IdNotFoundException(Tables.FLY.name()));

        var priceInDollars =  fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
        if(currency.equals("USD")){
            log.info("Price in USD: {}", priceInDollars);
            return priceInDollars;
        };
        var currencyDto = this.apiCurrencyConectorHelper.getCurrency(currency);
        var priceInCurrency = priceInDollars.multiply(currencyDto.getRates().get(currency));
        log.info("Price in {}: {}", currency, priceInCurrency);
        return priceInCurrency;
    }

    private TicketResponse toTicketResponse(TicketEntity ticketEntity) {
       var response = new TicketResponse();
        BeanUtils.copyProperties(ticketEntity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(ticketEntity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }


    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
}
