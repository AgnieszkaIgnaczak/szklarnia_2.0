package com.szklarnia.exception_handler;

//customowy wyjątek, który można rzucać na całej aplikacji
//"Reksio szuka ogrodnika, bo mu właściciel ciągle znika. A ogrodnik ma błędy w kodzie i zaginął w ogrodzie. Reksiu! Reksiu! Nie płacz proszę, już poprawki nanoszę."

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
