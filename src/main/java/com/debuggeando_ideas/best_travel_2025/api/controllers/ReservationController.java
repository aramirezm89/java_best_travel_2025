package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ReservationResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "reservation")
public class ReservationController {
    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> post(@Valid @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(this.reservationService.read(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> put(@Valid @PathVariable UUID id, @RequestBody ReservationRequest request){
        return ResponseEntity.ok(this.reservationService.update(request, id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam Long hotelId){
      var price = this.reservationService.findPrice(hotelId);
      return ResponseEntity.ok(Map.of("price", price));
    }


}
