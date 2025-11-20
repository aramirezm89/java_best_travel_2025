package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ReservationResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "reservation")
@Tag(name = "Reservation", description = "Reservation Management")
public class ReservationController {
    private final IReservationService reservationService;

    @ApiResponse(responseCode = "200", description = "Reservation created successfully")
    @ApiResponse(responseCode = "400", description = "Reservation not created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping
    public ResponseEntity<ReservationResponse> post(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(this.reservationService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(this.reservationService.read(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ReservationResponse> put(@Valid @PathVariable UUID id, @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(this.reservationService.update(request, id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "return a reservation price giving a hotel id")
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(@RequestParam Long hotelId,@RequestHeader(required = false) String currency){
        if(currency == null){
            currency = "USD";
        }
      var price = this.reservationService.findPrice(hotelId,currency.toUpperCase());
      return ResponseEntity.ok(Map.of("price", price));
    }


}
