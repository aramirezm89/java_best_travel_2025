package com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.HotelResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse> {

    Set<HotelResponse> readByRatingGreaterThan(Integer rating);
}
