package com.debuggeando_ideas.best_travel_2025.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tour")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ReservationEntity> reservations;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TicketEntity> tickets;


    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;

    @PrePersist
    public void updateFk(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(reservation -> reservation.setTour(this));
    }
    @PreRemove
    public void removeFK() {
        this.tickets.forEach(ticket -> ticket.setTour(null));
        this.reservations.forEach(reservation -> reservation.setTour(null));
    }

    public void addTicket(TicketEntity ticket) {
        if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
        this.tickets.add(ticket);
        ticket.setTour(this);
    }


    // borra un ticket de la lista tickets y lo quita de la relacion
    public void removeTicketById(UUID id) {
        if(Objects.isNull(this.tickets)) return;
        this.tickets.forEach(ticket ->{
            if(ticket.getId().equals(id)) ticket.setTour(null);
        });

    }

    public void addReservation(ReservationEntity reservation) {
        if(Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
        this.reservations.add(reservation);
        reservation.setTour(this);  // Sincroniza AMBOS lados inmediatamente
    }


    public void removeReservationById(UUID reservationId) {
        if(Objects.isNull(this.reservations)) return;
        this.reservations.forEach(reservation -> {
            if(reservation.getId().equals(reservationId)) reservation.setTour(null);
        });
    }


}
