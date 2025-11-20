package com.debuggeando_ideas.best_travel_2025.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configs/api_currency.properties")
public class PropertiesConfig {
}
