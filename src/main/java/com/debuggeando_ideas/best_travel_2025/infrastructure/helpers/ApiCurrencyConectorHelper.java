package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import com.debuggeando_ideas.best_travel_2025.infrastructure.dtos.CurrencyDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiCurrencyConectorHelper {

    private final WebClient currencyWebClient;

    @Value("${apilayer_base_currency}")
    private String baseCurrency;

    public ApiCurrencyConectorHelper(@Qualifier("apiCurrencyWebClient") WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    private static final String CURRENCY_API_URI = "/exchangerates_data/latest";


    public CurrencyDto getCurrency(String currency) {
        var response = this.currencyWebClient.get()
                .uri(uri ->
                        uri.path(CURRENCY_API_URI)
                                .queryParam("base", this.baseCurrency)
                                .queryParam("symbol", currency)
                                .build())
                .retrieve()
                .bodyToMono(CurrencyDto.class)
                .block();
        return  response;
    }
}
