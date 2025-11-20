package com.debuggeando_ideas.best_travel_2025.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CurrencyDto implements Serializable {
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("rates")
    private Map<String, BigDecimal> rates;

}
