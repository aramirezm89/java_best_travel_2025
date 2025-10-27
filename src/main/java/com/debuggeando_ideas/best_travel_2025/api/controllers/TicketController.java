package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.ITicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "tickets")
@AllArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(this.ticketService.create(ticketRequest));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(this.ticketService.read(id));
    }
    @PutMapping(path = "{id}")
    public ResponseEntity<TicketResponse> put(@PathVariable UUID id, @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(this.ticketService.update(ticketRequest, id));
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
