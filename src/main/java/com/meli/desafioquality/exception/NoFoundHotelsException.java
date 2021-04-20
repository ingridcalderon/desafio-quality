package com.meli.desafioquality.exception;

public class NoFoundHotelsException extends ReservationException{
    public NoFoundHotelsException(String msg) {
        super(404, msg);
    }
}
