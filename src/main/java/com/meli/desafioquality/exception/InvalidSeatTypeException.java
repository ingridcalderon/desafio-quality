package com.meli.desafioquality.exception;

public class InvalidSeatTypeException extends ReservationException{
    public InvalidSeatTypeException(String msg) {
        super(400, msg);
    }
}
