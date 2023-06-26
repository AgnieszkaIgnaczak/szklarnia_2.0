package com.szklarnia.exception_handler;

//customowy wyjątek, który można rzucać na całej aplikacji

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
