package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IFlyService;
import com.debuggeando_ideas.best_travel_2025.util.enums.SortType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(path = "fly")
@Tag(name = "Fly", description = "Fly Management")
public class FlyController {

    private final IFlyService flyService;

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAllFlights(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType) {

        if (sortType == null) {
            sortType = SortType.NONE;
        }
        var response = this.flyService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "less_price")
    public ResponseEntity<Set<FlyResponse>> getLessPriceFlights(@RequestParam BigDecimal price) {
        var response = this.flyService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPriceFlights(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        var response = this.flyService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "origin_destiny_flights")
    public ResponseEntity<Set<FlyResponse>> getOriginDestinyFlights(@RequestParam String origin, @RequestParam String destiny) {
        var response = this.flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

}
