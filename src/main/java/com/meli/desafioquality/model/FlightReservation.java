package com.meli.desafioquality.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightReservation {
    private String dateFrom;
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private int seats;
    private String seatType;
    private List<Person> people;
    private PaymentMethod paymentMethod;
}
