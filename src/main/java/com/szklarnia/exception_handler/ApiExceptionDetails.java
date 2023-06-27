package com.szklarnia.exception_handler;

//wyjątek

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiExceptionDetails {

    private final String message;
    //private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiExceptionDetails(String message, HttpStatus httpStatus, ZonedDateTime timestamp) { //Throwable throwable,
        this.message = message;
        //this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

//    public Throwable getThrowable() {
//        return throwable;
//    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
