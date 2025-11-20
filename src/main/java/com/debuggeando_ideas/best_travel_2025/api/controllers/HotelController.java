package com.debuggeando_ideas.best_travel_2025.api.controllers;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.HotelResponse;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IHotelService;
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
@RequestMapping(path = "hotel")
@Tag(name = "Hotel", description = "Hotel Management")
public class HotelController {
    private final IHotelService hotelService;
    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAllHotels(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType) {

        if (sortType == null) {
            sortType = SortType.NONE;
        }
        var response = this.hotelService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "less_price")
    public ResponseEntity<Set<HotelResponse>> getLessPriceHotels(@RequestParam BigDecimal price) {
        var response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "between_price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPriceHotels(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        var response = this.hotelService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "rating")
    public ResponseEntity<Set<HotelResponse>> getRatingHotels(@RequestParam Integer rating) {
        if(rating > 4) rating = 4;
        if(rating < 1) rating = 1;
        var response = this.hotelService.readByRatingGreaterThan(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
