package com.debuggeando_ideas.best_travel_2025.util.Exceptions;

public class ApiLayerException extends RuntimeException {
    public ApiLayerException(String message) {
        super(message);
    }

    public ApiLayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
