package com.meli.desafioquality.repository;

import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.NoFoundHotelsException;

import java.io.FileNotFoundException;
import java.util.List;

public interface HotelReservationRepository {
    List<HotelDTO> getHotels() throws FileNotFoundException;

    HotelDTO getHotelByCode(String code) throws InvalidQueryException, NoFoundHotelsException;

    void updateHotelByCode(String code) throws InvalidQueryException;
}
