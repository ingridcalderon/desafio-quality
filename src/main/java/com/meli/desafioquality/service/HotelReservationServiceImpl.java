package com.meli.desafioquality.service;

import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.dto.HotelReservationDTO;
import com.meli.desafioquality.dto.StatusCodeDTO;
import com.meli.desafioquality.enums.FilterEnum;
import com.meli.desafioquality.exception.*;
import com.meli.desafioquality.model.HotelBooking;
import com.meli.desafioquality.model.HotelReservationResponse;
import com.meli.desafioquality.repository.HotelReservationRepository;
import com.meli.desafioquality.utils.FilterHotels;
import com.meli.desafioquality.utils.validator.DateValidator;
import com.meli.desafioquality.utils.validator.EmailValidator;
import com.meli.desafioquality.utils.validator.PaymentMethodValidator;
import com.meli.desafioquality.utils.validator.RoomTypeValidator;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class HotelReservationServiceImpl implements HotelReservationService {

    private final HotelReservationRepository hotelReservationRepository;

    public HotelReservationServiceImpl(HotelReservationRepository hotelReservationRepository) {
        this.hotelReservationRepository = hotelReservationRepository;
    }
    @Override
    public List<HotelDTO> getHotels(Map<String, String> filters) throws ReservationException, FileNotFoundException {
        List<HotelDTO> hotels = hotelReservationRepository.getHotels();

        if(filters.size() > 0){
            for(Map.Entry<String,String> filter : filters.entrySet()){
                if(filter.getKey().equals(FilterEnum.DATE_FROM.getDescription())){
                    String dateTo = filters.get(FilterEnum.DATE_TO.getDescription());
                    hotels = FilterHotels.byDate(filter.getValue(), dateTo, hotels);
                }
                if(filter.getKey().equals(FilterEnum.DESTINATION.getDescription())){
                    hotels = FilterHotels.byDestination(hotels, filter.getValue());
                }
            }
        }

        return hotels;
    }

    @Override
    public HotelReservationResponse sendBookingRequest(HotelReservationDTO bookingRequest) throws DepartureDateGreaterArrivalDateException, InvalidDateFormatException, FileNotFoundException, InvalidQueryException, InvalidEmailException, NoFoundHotelsException {
        this.validateBooking(bookingRequest);

        HotelDTO hotelToBook = hotelReservationRepository.getHotelByCode(bookingRequest.getBooking().getHotelCode());
        double amount = hotelToBook.getPricePerNight() * DateValidator.daysBetween(bookingRequest.getBooking().getDateFrom(), bookingRequest.getBooking().getDateTo());
        double interest = PaymentMethodValidator.getInterest(bookingRequest.getBooking().getPaymentMethod().getType(), bookingRequest.getBooking().getPaymentMethod().getDues());
        double total = amount + (amount * interest);

        //Actualizar campo reserved
        hotelReservationRepository.updateHotelByCode(bookingRequest.getBooking().getHotelCode());

        StatusCodeDTO statusCode = new StatusCodeDTO(200, "El proceso terminó satisfactoriamente");

        HotelReservationResponse response = new HotelReservationResponse();
        response.setUserName(bookingRequest.getUserName());
        response.setAmount(amount);
        response.setInterest(interest);
        response.setTotal(total);
        response.setBooking(bookingRequest.getBooking());
        response.setStatusCode(statusCode);

        return response;
    }

    private void validateBooking(HotelReservationDTO bookingRequest) throws DepartureDateGreaterArrivalDateException, InvalidDateFormatException, FileNotFoundException, InvalidQueryException, InvalidEmailException {
        HotelBooking booking = bookingRequest.getBooking();

        boolean validDates = DateValidator.validateDates(booking.getDateFrom(), booking.getDateTo());
        boolean existDestination = hotelReservationRepository.getHotels().stream()
                .anyMatch(h -> h.getDestination().equals(booking.getDestination()));
        boolean validRoomType = RoomTypeValidator.canBeBookedForXPeople(booking.getRoomType(), booking.getPeopleAmount());
        if(!validRoomType){
            throw new InvalidQueryException("El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella");
        }
        try {
            int validAmount = booking.getPeopleAmount();
        } catch (NumberFormatException e){
            throw new InvalidQueryException("La cantidad de personas debe ser un valor numérico");
        }

        EmailValidator.validateEmail(bookingRequest.getUserName());
    }

}

