package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TourResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
public class TourController {

    private final ITourService tourService;
    @PostMapping
    public ResponseEntity<TourResponse> createTour(@RequestBody TourRequest tourRequest){
        return ResponseEntity.ok(this.tourService.create(tourRequest));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> getTour(@PathVariable Long id){
        return ResponseEntity.ok(this.tourService.read(id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        var response = this.tourService.addTicket(tourId, flyId);
        return ResponseEntity.ok(Map.of("ticketId", response));
    }


    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId){
        this.tourService.removeReservation(tourId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long hotelId, @RequestParam Integer totalDays){
        var response = this.tourService.addReservation(tourId, hotelId, totalDays);
        return ResponseEntity.ok(Map.of("ticketId", response));
    }
}
