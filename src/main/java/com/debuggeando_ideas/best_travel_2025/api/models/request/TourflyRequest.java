package com.debuggeando_ideas.best_travel_2025.api.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourflyRequest implements Serializable {
    @Positive(message = "fly id must be positive")
    @NotNull(message = "fly id is required")
    private Long id;
}
