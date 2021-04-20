package com.meli.desafioquality.exception;

public class NoFoundFlightsException extends ReservationException{
    public NoFoundFlightsException(String msg) {
        super(404, msg);
    }
}
