package com.szklarnia.exception_handler;

//obsługiwacz customowego wyjątku

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

//klasa do handlowania wyjątków
@ControllerAdvice
public class ApiExceptionHandler {

    //hej Spring to jest metoda, która obsługuje wyjątek ApiRequestException
    @ExceptionHandler(value = {ApiRequestException.class}) //fed into ApiRequestException
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException are) {
        //payload to send inside the Response Entity(payload containing exception details)

        HttpStatus badRequest = HttpStatus.BAD_REQUEST; //replaces all occurrences

        //object which contains payload information
        ApiException apiException = new ApiException(
                are.getMessage(),
                //are, //Throwable
                //HttpStatus.BAD_REQUEST, //ctl + shift + space
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //return response entity
        return new ResponseEntity<>(apiException, badRequest); //HttpStatus.BAD_REQUEST

    }
}
