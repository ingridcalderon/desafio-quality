package com.meli.desafioquality.dto;

import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.validator.SeatTypeValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private double pricePerPerson;
    private String departureDate;
    private String returnDate;
    private boolean reserved;

    public FlightDTO(String flightNumber, String origin, String destination, String seatType, String pricePerPerson, String departureDate, String returnDate, String reserved) throws ReservationException {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.seatType = SeatTypeValidator.validSeatType(seatType);
        this.pricePerPerson = Float.parseFloat(pricePerPerson.substring(1).replaceAll("\\.",""));
        this.departureDate = new DateDTO(departureDate).toString();
        this.returnDate = new DateDTO(returnDate).toString();
        this.reserved = !reserved.equals("NO");
    }
}
