package com.meli.desafioquality.service;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.dto.FlightReservationDTO;
import com.meli.desafioquality.exception.*;
import com.meli.desafioquality.model.FlightReservationResponse;
import com.meli.desafioquality.model.HotelReservationResponse;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface FlightReservationService {
    List<FlightDTO> getFlights(Map<String, String> queryParams) throws ReservationException, FileNotFoundException;

    FlightReservationResponse sendFlightReservationRequest(FlightReservationDTO flightReservationRequest) throws ReservationException, FileNotFoundException;
}
