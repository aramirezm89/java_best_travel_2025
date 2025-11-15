package com.debuggeando_ideas.best_travel_2025.api.controllers.error_handler;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.BaseErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.ForbiddenCustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenRequestController {

    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleForbiddenCustomerException(ForbiddenCustomerException ex){
        return ErrorResponse
                .builder()
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
    }
}
