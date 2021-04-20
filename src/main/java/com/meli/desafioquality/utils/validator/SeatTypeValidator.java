package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidSeatTypeException;

import java.util.HashMap;

public class SeatTypeValidator {
    private static final HashMap<String, String> seatTypes = getSeatTypes();

    private static HashMap<String, String> getSeatTypes(){
        HashMap<String, String> seats = new HashMap<>();
        seats.put("Economy", "ECONOMY");
        seats.put("Business", "BUSINESS");
        seats.put("First", "FIRST");
        return seats;
    }

    public static String validSeatType(String seatType) throws InvalidSeatTypeException {
        if(!isValidSeatTypeByKey(seatType)){
            throw new InvalidSeatTypeException("Tipo de habitación inválida");
        }

        return seatType;
    }

    private static boolean isValidSeatTypeByKey(String seatType){
        return seatTypes.containsKey(seatType);
    }
}
