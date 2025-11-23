package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITicketService;
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

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
@Tag(name = "Ticket", description = "Ticket Management")
public class TicketController {
    private final ITicketService ticketService;

    @ApiResponse(responseCode = "200", description = "Ticket created successfully")
    @ApiResponse(responseCode = "400", description = "Ticket not created",content = {
            @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping
    public ResponseEntity<TicketResponse> post(@Valid @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(this.ticketService.create(ticketRequest));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(this.ticketService.read(id));
    }
    @PutMapping(path = "{id}")
    public ResponseEntity<TicketResponse> put(@Valid @PathVariable UUID id, @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(this.ticketService.update(ticketRequest, id));
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getPriceByFlyId(@RequestParam Long flyId,@RequestHeader(required = false) String currency) {
        if(currency == null){
            currency = "USD";
        }
        return ResponseEntity.ok(Map.of("price", this.ticketService.findPrice(flyId, currency)));
    }
}
