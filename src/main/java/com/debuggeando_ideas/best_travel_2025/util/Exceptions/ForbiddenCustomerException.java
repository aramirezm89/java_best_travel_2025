package com.debuggeando_ideas.best_travel_2025.util.Exceptions;

public class ForbiddenCustomerException extends RuntimeException {
    public ForbiddenCustomerException() {
        super("this customer is blocked");
    }
}
