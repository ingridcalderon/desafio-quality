package com.meli.desafioquality.exception;

import lombok.Data;

@Data
public class ReservationException extends Exception{
    private int code;
    private String message;

    public ReservationException(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
