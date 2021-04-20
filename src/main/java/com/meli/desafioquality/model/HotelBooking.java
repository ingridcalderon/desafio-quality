package com.meli.desafioquality.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelBooking {
    private String dateFrom;
    private String dateTo;
    private String destination;
    private String hotelCode;
    private int peopleAmount;
    private String roomType;
    private List<Person> people;
    private PaymentMethod paymentMethod;
}
