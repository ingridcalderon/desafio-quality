package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidRoomTypeException;

import java.util.HashMap;

public class RoomTypeValidator {
    private static final HashMap<String, String> roomTypes = getRoomTypes();

    private static HashMap<String, String> getRoomTypes(){
        HashMap<String, String> rooms = new HashMap<>();
        rooms.put("Single", "SINGLE");
        rooms.put("Doble", "DOUBLE");
        rooms.put("Triple", "TRIPLE");
        rooms.put("Múltiple", "MULTIPLE");
        return rooms;
    }

    public static String validRoomType(String roomType) throws InvalidRoomTypeException {
        if(!isValidNameRoomTypeByKey(roomType)){
            throw new InvalidRoomTypeException("Tipo de habitación inválida");
        }

        return roomType;
    }

    private static boolean isValidNameRoomTypeByKey(String roomType){
        return roomTypes.containsKey(roomType);
    }

    private static boolean isValidNameRoomTypeByValue(String roomType){
        return roomTypes.values().contains(roomType);
    }

    public static boolean canBeBookedForXPeople(String roomType, int people){
        if(isValidNameRoomTypeByValue(roomType)){
            return (roomTypes.get("Single").equals(roomType) && people == 1) ||
                    (roomTypes.get("Doble").equals(roomType) && people == 2) ||
                    (roomTypes.get("Triple").equals(roomType) && people == 3) ||
                    (roomTypes.get("Múltiple").equals(roomType) && (people >= 4 && people <= 10));
        }
        return false;
    }
}
