package com.meli.desafioquality.exception;

public class InvalidRoomTypeException extends ReservationException{
    public InvalidRoomTypeException(String msg) {
        super(400, msg);
    }
}
