package com.debuggeando_ideas.best_travel_2025.api.controllers.error_handler;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.BaseErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorResponse;
import com.debuggeando_ideas.best_travel_2025.api.models.responses.ErrorsResponse;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.ApiLayerException;
import com.debuggeando_ideas.best_travel_2025.util.Exceptions.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception) {
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }



    //manejo de errores de validacion de argumentos DTO models.request
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseErrorResponse handleArgumentNotValid(MethodArgumentNotValidException exception){
        var errors  = new ArrayList<String>();
        exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(ApiLayerException.class)
    public BaseErrorResponse handleApiLayerException(ApiLayerException exception) {
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
    @ExceptionHandler(NullPointerException.class)
    public BaseErrorResponse handleNullPointerException(NullPointerException exception) {
        return ErrorResponse
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
