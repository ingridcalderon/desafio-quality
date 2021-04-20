package com.meli.desafioquality.model;

import com.meli.desafioquality.dto.StatusCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //Borrar
public class HotelReservationResponse {
    private String userName;
    private double amount;
    private double interest;
    private double total;
    private HotelBooking booking;
    private StatusCodeDTO statusCode;
}
