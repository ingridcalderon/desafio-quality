package com.meli.desafioquality.dto;

import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.validator.RoomTypeValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public HotelDTO(List<String> args) throws ReservationException {
        this.hotelCode = args.get(0);
        this.name = args.get(1);
        this.destination = args.get(2);
        this.roomType = RoomTypeValidator.validRoomType(args.get(3));
        this.pricePerNight = Float.parseFloat(args.get(4).substring(1).replaceAll("\\.",""));
        this.availableFrom = new DateDTO(args.get(5)).toString();
        this.availableTo = new DateDTO(args.get(6)).toString();
        this.reserved = !args.get(7).equals("NO");
    }
}
