package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ReservationResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "reservation")
public class ReservationController {
    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> post(@RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(this.reservationService.read(id));
    }
}
