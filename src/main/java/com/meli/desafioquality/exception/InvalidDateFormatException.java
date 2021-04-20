package com.meli.desafioquality.exception;

public class InvalidDateFormatException extends ReservationException{
    public InvalidDateFormatException(String msg) {
        super(400, msg);
    }
}
