package com.debuggeando_ideas.best_travel_2025.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 18, max = 20, message = "The client id must be between 18 and 20 characters")
    @NotBlank(message = "id client is required")
    private String customerId;
    @Size(min = 1, message = "At least one flight is required")
    private Set<TourflyRequest> flights;
    @Size(min = 1, message = "At least one hotel is required")
    private Set<TourHotelRequest> hotels;
    @Email(message = "The email is not valid")
    @NotBlank(message = "email is required")
    private String email;
}
