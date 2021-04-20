package com.meli.desafioquality.utils;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.exception.NoFoundFlightsException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.validator.DateValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterFlights {

    public static List<FlightDTO> byDestination(List<FlightDTO> flights, String destination) throws NoFoundFlightsException {
        List<FlightDTO> flightsWithoutReserved = withoutReserved(flights);

        List<FlightDTO> flightsAux = flightsWithoutReserved.stream()
                .filter(flight -> flight.getDestination().equals(destination))
                .collect(Collectors.toList());
        if(flights.size() == 0){
            throw new NoFoundFlightsException("El destino elegido no existe");
        }
        return flightsAux;
    }

    public static List<FlightDTO> byDate(String dateFrom, String dateTo, List<FlightDTO> flights) throws ReservationException {
        boolean validDates = DateValidator.validateDates(dateFrom, dateTo);
        List<FlightDTO> flightsWithoutReserved = withoutReserved(flights);

        List<FlightDTO> flightsAux = new ArrayList<>();
        if(validDates){
            for(FlightDTO flight: flightsWithoutReserved){
                if(datesInRange(dateFrom, dateTo, flight)){
                    flightsAux.add(flight);
                }
            }
            if(flightsAux.size() == 0){
                throw new NoFoundFlightsException("No se encontró ningún vuelo para la fecha elegida");
            }
        }
        return flightsAux;
    }

    private static List<FlightDTO> withoutReserved(List<FlightDTO> flights) {
        List<FlightDTO> flightsWithoutReserved = flights.stream()
                .filter(flight -> !flight.isReserved())
                .collect(Collectors.toList());

        return flightsWithoutReserved;
    }

    private static boolean datesInRange(String dateFrom, String dateTo, FlightDTO flight) {
        return  DateValidator.dateLessThanOtherDate2(flight.getDepartureDate(), dateFrom) &&
                DateValidator.dateLessThanOtherDate2(dateTo, flight.getReturnDate());
    }

}