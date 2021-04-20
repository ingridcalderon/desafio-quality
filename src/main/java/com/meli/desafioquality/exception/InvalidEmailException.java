package com.meli.desafioquality.exception;

public class InvalidEmailException extends ReservationException{
    public InvalidEmailException(String msg) {
        super(400, msg);
    }
}
