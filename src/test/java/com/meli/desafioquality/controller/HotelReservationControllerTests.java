package com.meli.desafioquality.controller;

import com.meli.desafioquality.dto.DateDTO;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.dto.HotelReservationDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.model.HotelBooking;
import com.meli.desafioquality.model.PaymentMethod;
import com.meli.desafioquality.model.Person;
import com.meli.desafioquality.model.HotelReservationResponse;
import com.meli.desafioquality.service.HotelReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HotelReservationControllerTests {
    @Mock
    private HotelReservationService hotelReservationServiceMock;
    private HotelDTO hotel1;
    private HotelDTO hotel2;
    private List<HotelDTO> hotels;
    private HotelReservationController hotelReservationController;
    private Map<String, String> withoutParams;
    private Map<String, String> validParams;
    private Map<String, String> invalidParams;
    private HotelReservationDTO reservation;

    @Before
    public void setUp() throws ReservationException {
        hotelReservationController = new HotelReservationController(hotelReservationServiceMock);
        hotel1 = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble", "$6300",
                "10/02/2021", "20/03/2021", "NO");
        hotel2 = new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires", "Single", "$5435",
                "10/02/2021", "19/03/2021", "NO");
        hotels = Arrays.asList(hotel1, hotel2);

        withoutParams = new HashMap<>();

        validParams = new HashMap<>();
        validParams.put("destination", "Buenos Aires");

        invalidParams = new HashMap<>();
        invalidParams.put("badParam", "badValue");

        Person person1 = new Person("12345678", "Pepito", "Suarez", new DateDTO("18/07/1990"), "pepitosuarez@gmail.com");
        Person person2 = new Person("87654321", "Pepita", "Suarez", new DateDTO("23/09/1992"), "pepitasuarez@gmail.com");
        List<Person> people = Arrays.asList(person1, person2);
        PaymentMethod paymentMethod = new PaymentMethod("DEBIT", "1234-5678-1234", 1);
        HotelBooking hotelBooking = new HotelBooking("12/02/2021", "14/02/2021", "Buenos Aires", "HB-0001",
                2, "DOUBLE", people, paymentMethod);

        reservation = new HotelReservationDTO("ingrid.calderon@mercadolibre.com", hotelBooking);
    }

    @Test
    @DisplayName("getHotels without params call one time to HotelReservationService")
    public void whenGetHotelsWithoutParamsThenCallOneTimeToHotelReservationServiceTest() throws IOException, ReservationException {
        when(hotelReservationServiceMock.getHotels(withoutParams)).thenReturn(hotels);

        hotelReservationController.getHotels(withoutParams);
        verify(hotelReservationServiceMock, times(1)).getHotels(withoutParams);
        assertEquals(hotelReservationController.getHotels(withoutParams).getStatusCode().value(), 200);
        assertEquals(hotelReservationController.getHotels(withoutParams).getBody(), hotels);
    }

    @Test
    @DisplayName("getHotels with valid params call one time to HotelReservationService")
    public void whenGetHotelsWithValidParamsThenCallOneTimeToHotelReservationServiceAndReturnHotelsTest() throws IOException, ReservationException {
        when(hotelReservationServiceMock.getHotels(withoutParams)).thenReturn(Arrays.asList(hotel2));

        hotelReservationController.getHotels(validParams);
        verify(hotelReservationServiceMock, times(1)).getHotels(validParams);
        assertEquals(hotelReservationController.getHotels(validParams).getStatusCode().value(), 200);
        assertEquals(hotelReservationController.getHotels(withoutParams).getBody(), Arrays.asList(hotel2));
    }

    @Test
    @DisplayName("getHotels with invalid params don't call to HotelReservationService")
    public void whenGetHotelsWithInvalidParamsThenCallZeroTimeToHotelReservationServiceAndThrowExceptionTest() throws IOException, ReservationException {
        Exception ex = assertThrows(InvalidQueryException.class, () -> hotelReservationController.getHotels(invalidParams));
        verify(hotelReservationServiceMock, times(0)).getHotels(invalidParams);
        assertEquals("Pedido inválido", ex.getMessage());
    }

    @Test
    @DisplayName("send booking request with valid param")
    public void whenSendBookingRequestWithValidParamsThenCallOneTimeToHotelReservationServiceAndReturnReservationResponseTest() throws Exception {
        HotelReservationResponse response = new HotelReservationResponse();

        when(hotelReservationServiceMock.sendBookingRequest(reservation)).thenReturn(response);

        hotelReservationController.sendBookingRequest(reservation);
        verify(hotelReservationServiceMock, times(1)).sendBookingRequest(reservation);
        assertEquals(hotelReservationController.sendBookingRequest(reservation).getStatusCode().value(), 200);
        assertEquals(hotelReservationController.sendBookingRequest(reservation).getBody(), response);
    }

    @Test
    @DisplayName("send booking request with invalid param")
    public void whenSendBookingRequestWithInvalidParamsThenCallZeroTimeToHotelReservationServiceAndThrowExceptionTest() throws IOException, ReservationException {
        reservation = null;
        Exception ex = assertThrows(InvalidQueryException.class, () -> hotelReservationController.sendBookingRequest(reservation));
        verify(hotelReservationServiceMock, times(0)).sendBookingRequest(reservation);
        assertEquals("Tiene que ingresar un pedido de reserva", ex.getMessage());
    }
}