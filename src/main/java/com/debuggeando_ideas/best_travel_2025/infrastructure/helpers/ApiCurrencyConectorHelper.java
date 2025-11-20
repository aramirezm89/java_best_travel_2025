package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import com.debuggeando_ideas.best_travel_2025.infrastructure.dtos.CurrencyDto;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.ApiLayerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class ApiCurrencyConectorHelper {

    private final WebClient currencyWebClient;

    @Value("${apilayer_base_currency}")
    private String baseCurrency;

    public ApiCurrencyConectorHelper(@Qualifier("apiCurrencyWebClient") WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    private static final String CURRENCY_API_URI = "/exchangerates_data/latest";


    public CurrencyDto getCurrency(String currency) {
       try {
           var response = this.currencyWebClient.get()
                   .uri(uri ->
                           uri.path(CURRENCY_API_URI)
                                   .queryParam("base", this.baseCurrency)
                                   .queryParam("symbols", currency)
                                   .build())
                   .retrieve()
                   .bodyToMono(CurrencyDto.class)
                   .block();
           return  response;
       } catch (WebClientResponseException e) {
           String bodyError = e.getResponseBodyAsString();
           log.error("Error getting currency {}", bodyError);
           throw new ApiLayerException("Error ApiLayerApi: " + bodyError);
       }
    }
}
