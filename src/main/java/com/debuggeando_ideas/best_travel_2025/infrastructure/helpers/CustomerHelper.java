package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import com.debuggeando_ideas.best_travel_2025.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.IdNotFoundException;
import com.debuggeando_ideas.best_travel_2025.util.enums.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@AllArgsConstructor
@Slf4j
public class CustomerHelper {

    private CustomerRepository customerRepository;

    public void increase(String CustomerId, Class<?> type){

        var customerToUpdate = this.customerRepository.findById(CustomerId).orElseThrow(() -> new IdNotFoundException(Tables.CUSTOMER.name()));
       log.info("class: {}", type.getSimpleName());
        switch (type.getSimpleName()) {
             case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() + 1);
             case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() + 1);
             case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() + 1);
        }

        this.customerRepository.save(customerToUpdate);
    }
}
