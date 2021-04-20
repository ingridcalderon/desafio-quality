package com.meli.desafioquality.dto;

import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.validator.RoomTypeValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private String hotelCode;
    private String name;
    private String destination;
    private String roomType;
    private double pricePerNight;
    private String availableFrom;
    private String availableTo;
    private boolean reserved;

    public HotelDTO(String hotelCode, String name, String destination, String roomType, String pricePerNight, String availableFrom, String availableTo, String reserved) throws ReservationException {
        this.hotelCode = hotelCode;
        this.name = name;
        this.destination = destination;
        this.roomType = RoomTypeValidator.validRoomType(roomType);
        this.pricePerNight = Float.parseFloat(pricePerNight.substring(1).replaceAll("\\.",""));
        this.availableFrom = new DateDTO(availableFrom).toString();
        this.availableTo = new DateDTO(availableTo).toString();
        this.reserved = !reserved.equals("NO");
    }
}
