package com.debuggeando_ideas.best_travel_2025.util.Exceptions;

public class IdNotFoundException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Record no exist in %s";

    public IdNotFoundException(String entityName){
        super(String.format(ERROR_MESSAGE, entityName));
    }
}
