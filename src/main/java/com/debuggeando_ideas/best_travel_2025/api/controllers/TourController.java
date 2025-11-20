package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TourResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
@Tag(name = "Tour", description = "Tour Management")
public class TourController {

    private final ITourService tourService;

    @ApiResponse(responseCode = "200", description = "Tour created successfully")
    @ApiResponse(responseCode = "400", description = "Tour not created",content = {
            @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(summary = "Create a new tour based in a Hotel and a Fly List")
    @PostMapping
    public ResponseEntity<TourResponse> createTour(@Valid @RequestBody TourRequest tourRequest){
        return ResponseEntity.ok(this.tourService.create(tourRequest));
    }


    @Operation(summary = "Return a list of tours")
    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> getTour(@PathVariable Long id){
        return ResponseEntity.ok(this.tourService.read(id));
    }


    @Operation(summary = "Delete a tour with the given id")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id){
        this.tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a ticket from a tour")
    @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        this.tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a ticket to a tour")
    @PatchMapping(path = "{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        var response = this.tourService.addTicket(tourId, flyId);
        return ResponseEntity.ok(Map.of("ticketId", response));
    }


    @Operation(summary = "Remove a reservation from a tour")
    @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId){
        this.tourService.removeReservation(tourId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add a reservation to a tour")
    @PatchMapping(path = "{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId, @PathVariable Long hotelId, @RequestParam Integer totalDays){
        var response = this.tourService.addReservation(tourId, hotelId, totalDays);
        return ResponseEntity.ok(Map.of("ticketId", response));
    }
}
