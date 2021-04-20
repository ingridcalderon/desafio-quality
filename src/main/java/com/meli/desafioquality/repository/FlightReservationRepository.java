package com.meli.desafioquality.repository;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.NoFoundHotelsException;

import java.io.FileNotFoundException;
import java.util.List;

public interface FlightReservationRepository {
    List<FlightDTO> getFlights() throws FileNotFoundException;

    FlightDTO getFlightByCode(String code) throws InvalidQueryException, NoFoundHotelsException;

    void updateFlightByCode(String code) throws InvalidQueryException;
}
