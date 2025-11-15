package com.debuggeando_ideas.best_travel_2025.api.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {

    @Size(min = 18, max = 20, message = "The client id must be between 18 and 20 characters")
    @NotBlank(message = "id client is required")
    private String clientId;
    @Positive(message = "hotel id must be positive")
    @NotNull(message = "hotel id is required")
    private Long hotelId;
    @Min(value = 1, message = "Min one days to make a reservation")
    @Max(value = 30, message = "Max 30 days to make a reservation")
    @NotNull(message = "total days is required")
    private Integer totalDays;
    @Email(message = "The email is not valid")
    private String email;
}
