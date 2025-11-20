package com.debuggeando_ideas.best_travel_2025.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${apilayer_base_url}")
    private String apiCurrencyBaseUrl;
    @Value("${apilayer_apikey}")
    private String apiCurrencyApiKey;
    @Value("${apilayer_apikey_header}")
    private String apiCurrencyApiKeyHeader;

    @Bean("apiCurrencyWebClient")
    public WebClient apiCurrencyWebClient() {
        return WebClient.builder()
                .baseUrl(apiCurrencyBaseUrl).
                defaultHeader(apiCurrencyApiKeyHeader, apiCurrencyApiKey)
                .build();
    }

    @Bean("baseWebClient")
    public WebClient baseWebClient() {
        return WebClient.builder().build();
    }
}
