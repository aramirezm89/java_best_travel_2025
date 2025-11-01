package com.debuggeando_ideas.best_travel_2025.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {
    private String customerId;
    private Set<TourflyRequest> flights;
    private Set<TourHotelRequest> hotels;
}
