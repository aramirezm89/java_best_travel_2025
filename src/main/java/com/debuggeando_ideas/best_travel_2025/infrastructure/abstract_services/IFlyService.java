package com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse>{

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
