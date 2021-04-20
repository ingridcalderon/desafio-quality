package com.meli.desafioquality.exception;

public class InvalidPaymentMethodException extends ReservationException{
    public InvalidPaymentMethodException(String msg) {
        super(400, msg);
    }
}
