package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.HotelResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ReservationResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IReservationService;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.ApiCurrencyConectorHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.BlackListHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.CustomerHelper;
import com.debuggeando_ideas.best_travel_2025.infrastructure.helpers.EmailHelper;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.ApiLayerException;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.IdNotFoundException;
import com.debuggeando_ideas.best_travel_2025.util.enums.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final ApiCurrencyConectorHelper apiCurrencyConectorHelper;
    private final EmailHelper emailHelper;
    @Override
    public ReservationResponse create(ReservationRequest request) {
        this.blackListHelper.isBlackListed(request.getClientId());
        var customer = this.customerRepository.findById(request.getClientId()).orElseThrow(() -> new IdNotFoundException(Tables.CUSTOMER.name()));
        var hotel = this.hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new IdNotFoundException(Tables.HOTEL.name()));
        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .hotel(hotel)
                .customer(customer)
                .build();

        var reservationPersisted = this.reservationRepository.save(reservationToPersist);
        log.info("Reservation persisted: {}", reservationPersisted);
        this.customerHelper.increase(customer.getDni(), ReservationService.class);

        if(request.getEmail() != null){
            this.emailHelper.sendEmail(request.getEmail(), customer.getFullName(), Tables.RESERVATION.name());
        }
        return this.toReservationResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservation = this.reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.RESERVATION.name()));
        log.info("Reservation by ID read: {}", reservation);
        return this.toReservationResponse(reservation);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID uuid) {

        var hotel = this.hotelRepository.findById(request.getHotelId()).orElseThrow(() -> new IdNotFoundException(Tables.HOTEL.name()));
        var reservationToUpdate = this.reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.RESERVATION.name()));

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)));
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);
        log.info("Reservation updated: {}", reservationUpdated);
        return this.toReservationResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID uuid) {
        var reservationToDelete = this.reservationRepository.findById(uuid).orElseThrow(() -> new IdNotFoundException(Tables.RESERVATION.name()));
        this.reservationRepository.delete(reservationToDelete);
        log.info("Reservation deleted: {}", reservationToDelete.getId());
    }

    ReservationResponse toReservationResponse(ReservationEntity reservationEntity){
       var reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(reservationEntity, reservationResponse);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(reservationEntity.getHotel(), hotelResponse);
        reservationResponse.setHotel(hotelResponse);
        return reservationResponse;

    }

    @Override
    public BigDecimal findPrice(Long hotelId, String currency) {
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException(Tables.HOTEL.name()));
        var priceInDollars =  hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));

        if(currency.equals("USD")){
            log.info("Price in USD: {}", priceInDollars);
            return priceInDollars;
        };

        var currencyDto = this.apiCurrencyConectorHelper.getCurrency(currency);


        log.info("API currency in {}, response {} : {}", currencyDto.getDate(),currency, currencyDto.getRates().get(currency));

        return priceInDollars.multiply(currencyDto.getRates().get(currency));

    }
    public static final BigDecimal charges_price_percentage =  BigDecimal.valueOf(0.20);


}
