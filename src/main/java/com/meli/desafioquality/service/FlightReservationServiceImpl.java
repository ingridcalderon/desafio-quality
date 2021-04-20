package com.meli.desafioquality.service;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.dto.FlightReservationDTO;
import com.meli.desafioquality.dto.StatusCodeDTO;
import com.meli.desafioquality.enums.FilterEnum;
import com.meli.desafioquality.exception.*;
import com.meli.desafioquality.model.FlightReservation;
import com.meli.desafioquality.model.FlightReservationResponse;
import com.meli.desafioquality.model.HotelReservationResponse;
import com.meli.desafioquality.repository.FlightReservationRepository;
import com.meli.desafioquality.utils.FilterFlights;
import com.meli.desafioquality.utils.validator.DateValidator;
import com.meli.desafioquality.utils.validator.EmailValidator;
import com.meli.desafioquality.utils.validator.PaymentMethodValidator;
import com.meli.desafioquality.utils.validator.SeatTypeValidator;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class FlightReservationServiceImpl implements FlightReservationService {

    private final FlightReservationRepository flightReservationRepository;

    public FlightReservationServiceImpl(FlightReservationRepository flightReservationRepository) {
        this.flightReservationRepository = flightReservationRepository;
    }

    @Override
    public List<FlightDTO> getFlights(Map<String, String> filters) throws ReservationException, FileNotFoundException {
        List<FlightDTO> flights = flightReservationRepository.getFlights();

        if(filters.size() > 0){
            for(Map.Entry<String,String> filter : filters.entrySet()){
                if(filter.getKey().equals(FilterEnum.DATE_FROM.getDescription())){
                    String dateTo = filters.get(FilterEnum.DATE_TO.getDescription());
                    flights = FilterFlights.byDate(filter.getValue(), dateTo, flights);
                }
                if(filter.getKey().equals(FilterEnum.DESTINATION.getDescription())){
                    flights = FilterFlights.byDestination(flights, filter.getValue());
                }
            }
        }

        return flights;
    }

    @Override
    public FlightReservationResponse sendFlightReservationRequest(FlightReservationDTO reservationRequest) throws ReservationException, FileNotFoundException {
        this.validateFlightReservation(reservationRequest);

        FlightDTO flightToReserve = flightReservationRepository.getFlightByCode(reservationRequest.getFlightReservation().getFlightNumber());
        double amount = flightToReserve.getPricePerPerson() * DateValidator.daysBetween(reservationRequest.getFlightReservation().getDateFrom(), reservationRequest.getFlightReservation().getDateTo());
        double interest = PaymentMethodValidator.getInterest(reservationRequest.getFlightReservation().getPaymentMethod().getType(), reservationRequest.getFlightReservation().getPaymentMethod().getDues());
        double total = amount + (amount * interest);

        //Actualizar campo reserved
        flightReservationRepository.updateFlightByCode(reservationRequest.getFlightReservation().getFlightNumber());

        StatusCodeDTO statusCode = new StatusCodeDTO(200, "El proceso terminó satisfactoriamente");

        FlightReservationResponse response = new FlightReservationResponse();
        response.setUserName(reservationRequest.getUserName());
        response.setAmount(amount);
        response.setInterest(interest);
        response.setTotal(total);
        response.setFlightReservation(reservationRequest.getFlightReservation());
        response.setStatusCode(statusCode);

        return response;
    }

    private void validateFlightReservation(FlightReservationDTO reservationRequest) throws DepartureDateGreaterArrivalDateException, InvalidDateFormatException, FileNotFoundException, InvalidQueryException, InvalidEmailException, InvalidSeatTypeException {
        FlightReservation reservation = reservationRequest.getFlightReservation();

        boolean validDates = DateValidator.validateDates(reservation.getDateFrom(), reservation.getDateTo());
        boolean existDestination = flightReservationRepository.getFlights().stream()
                .anyMatch(h -> h.getDestination().equals(reservation.getDestination()));
        boolean validSeatType = SeatTypeValidator.validSeatType(reservation.getSeatType()) == reservation.getSeatType();
        if(!validSeatType){
            throw new InvalidQueryException("El tipo de asiento seleccionada es inválido");
        }
        try {
            int validAmount = reservation.getSeats();
        } catch (NumberFormatException e){
            throw new InvalidQueryException("La cantidad de personas debe ser un valor numérico");
        }

        EmailValidator.validateEmail(reservationRequest.getUserName());
    }

}

