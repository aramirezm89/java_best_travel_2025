package com.debuggeando_ideas.best_travel_2025.api.models.responses;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends BaseErrorResponse {
    private String message;
}
