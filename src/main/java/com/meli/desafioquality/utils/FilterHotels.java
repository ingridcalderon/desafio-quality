package com.meli.desafioquality.utils;

import com.meli.desafioquality.exception.DepartureDateGreaterArrivalDateException;
import com.meli.desafioquality.exception.InvalidDateFormatException;
import com.meli.desafioquality.exception.NoFoundHotelsException;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.validator.DateValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterHotels {

    public static List<HotelDTO> byDestination(List<HotelDTO> hotels, String destination) throws NoFoundHotelsException {
        List<HotelDTO> hotelsWithoutReserved = withoutReserved(hotels);

        List<HotelDTO> hotelsAux = hotelsWithoutReserved.stream()
                .filter(hotel -> hotel.getDestination().equals(destination))
                .collect(Collectors.toList());
        if(hotelsAux.size() == 0){
            throw new NoFoundHotelsException("El destino elegido no existe");
        }
        return hotelsAux;
    }

    public static List<HotelDTO> byDate(String dateFrom, String dateTo, List<HotelDTO> hotels) throws ReservationException {
        boolean validDates = DateValidator.validateDates(dateFrom, dateTo);
        List<HotelDTO> hotelsWithoutReserved = withoutReserved(hotels);

        List<HotelDTO> hotelsAux = new ArrayList<>();
        if(validDates){
            for(HotelDTO hotel: hotelsWithoutReserved){
                if(datesInRange(dateFrom, dateTo, hotel)){
                    hotelsAux.add(hotel);
                }
            }
            if(hotelsAux.size() == 0){
                throw new NoFoundHotelsException("No se encontró ningún hotel para la fecha elegida");
            }
        }
        return hotelsAux;
    }

    private static List<HotelDTO> withoutReserved(List<HotelDTO> hotels) {
        List<HotelDTO> hotelsWithoutReserved = hotels.stream()
                .filter(hotel -> !hotel.isReserved())
                .collect(Collectors.toList());

        return hotelsWithoutReserved;
    }

    private static boolean datesInRange(String dateFrom, String dateTo, HotelDTO hotel) {
        return  DateValidator.dateLessThanOtherDate2(hotel.getAvailableFrom(), dateFrom) &&
                DateValidator.dateLessThanOtherDate2(dateTo, hotel.getAvailableTo());
    }

}