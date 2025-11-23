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
public class TicketRequest implements Serializable {

    @Positive(message = "The fly id must be positive")
    @NotNull(message = "The fly id is required")
    private Long flyId;
    @Size(min = 18, max = 20, message = "The client id must be between 18 and 20 characters")
    @NotBlank(message = "The client id is required")
    private String customerId;
    @Email(message = "The email is not valid")
    @NotBlank(message = "email is required")
    private String email;
}
