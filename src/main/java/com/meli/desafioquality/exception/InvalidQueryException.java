package com.meli.desafioquality.exception;

public class InvalidQueryException extends ReservationException{
    public InvalidQueryException(String msg) {
        super(400, msg);
    }
}
