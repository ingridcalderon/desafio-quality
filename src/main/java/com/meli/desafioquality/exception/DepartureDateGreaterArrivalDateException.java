package com.meli.desafioquality.exception;

public class DepartureDateGreaterArrivalDateException extends ReservationException{
    public DepartureDateGreaterArrivalDateException(String msg) {
        super(400, msg);
    }
}
