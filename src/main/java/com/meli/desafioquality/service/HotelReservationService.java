package com.meli.desafioquality.service;

import com.meli.desafioquality.exception.*;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.dto.HotelReservationDTO;
import com.meli.desafioquality.model.HotelReservationResponse;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface HotelReservationService {
    List<HotelDTO> getHotels(Map<String, String> queryParams) throws ReservationException, FileNotFoundException;

    HotelReservationResponse sendBookingRequest(HotelReservationDTO bookingRequest) throws DepartureDateGreaterArrivalDateException, InvalidDateFormatException, FileNotFoundException, InvalidQueryException, InvalidEmailException, NoFoundHotelsException;
}
