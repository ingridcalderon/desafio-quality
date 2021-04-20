package com.meli.desafioquality.dto;

import com.meli.desafioquality.model.FlightReservation;
import com.meli.desafioquality.model.HotelBooking;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightReservationDTO {
    private String userName;
    private FlightReservation flightReservation;
}
