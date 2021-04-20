package com.meli.desafioquality.dto;

import com.meli.desafioquality.model.HotelBooking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelReservationDTO {
    private String userName;
    private HotelBooking booking;
}
