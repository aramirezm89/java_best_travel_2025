package com.debuggeando_ideas.best_travel_2025.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "reservation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity implements  Serializable {
    @Id
    private UUID id;

    @Column(name = "date_reservation")
    private LocalDateTime  dateTimeReservation;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private BigDecimal price;
    private Integer totalDays;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;
    @ManyToOne
    @JoinColumn(name = "tour_id",nullable = true)
    private TourEntity tour;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
