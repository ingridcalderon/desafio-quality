package com.meli.desafioquality.controller;

import com.meli.desafioquality.dto.DateDTO;
import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.dto.FlightReservationDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.model.*;
import com.meli.desafioquality.service.FlightReservationService;
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
public class FlightReservationControllerTests {
    @Mock
    private FlightReservationService flightReservationServiceMock;
    private FlightDTO flight1;
    private FlightDTO flight2;
    private List<FlightDTO> flights;
    private FlightReservationController flightReservationController;
    private Map<String, String> withoutParams;
    private Map<String, String> validParams;
    private Map<String, String> invalidParams;
    private FlightReservationDTO reservation;

    @Before
    public void setUp() throws ReservationException {
        flightReservationController = new FlightReservationController(flightReservationServiceMock);
        flight1 = new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", "$6.500",
                "10/02/2021", "15/02/2021", "NO");
        flight2 = new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", "$43.200",
                "10/02/2021", "20/02/2021", "NO");
        flights = Arrays.asList(flight1, flight2);

        withoutParams = new HashMap<>();

        validParams = new HashMap<>();
        validParams.put("destination", "Buenos Aires");

        invalidParams = new HashMap<>();
        invalidParams.put("badParam", "badValue");

        Person person1 = new Person("12345678", "Pepito", "Suarez", new DateDTO("18/07/1990"), "pepitosuarez@gmail.com");
        Person person2 = new Person("87654321", "Pepita", "Suarez", new DateDTO("23/09/1992"), "pepitasuarez@gmail.com");
        List<Person> people = Arrays.asList(person1, person2);
        PaymentMethod paymentMethod = new PaymentMethod("DEBIT", "1234-5678-1234", 1);
        FlightReservation flightReservation = new FlightReservation("12/02/2021", "14/02/2021", "Buenos Aires", "Puerto Iguazú",
                "HB-0001", 2, "ECONOMY", people, paymentMethod);

        reservation = new FlightReservationDTO("ingrid.calderon@mercadolibre.com", flightReservation);
    }

    @Test
    @DisplayName("getFlights without params call one time to HotelReservationService")
    public void whenGetHotelsWithoutParamsThenCallOneTimeToHotelReservationServiceTest() throws IOException, ReservationException {
        when(flightReservationServiceMock.getFlights(withoutParams)).thenReturn(flights);

        flightReservationController.getFlights(withoutParams);
        verify(flightReservationServiceMock, times(1)).getFlights(withoutParams);
        assertEquals(flightReservationController.getFlights(withoutParams).getStatusCode().value(), 200);
        assertEquals(flightReservationController.getFlights(withoutParams).getBody(), flights);
    }

    @Test
    @DisplayName("getFlights with valid params call one time to HotelReservationService")
    public void whenGetHotelsWithValidParamsThenCallOneTimeToHotelReservationServiceAndReturnHotelsTest() throws IOException, ReservationException {
        when(flightReservationServiceMock.getFlights(withoutParams)).thenReturn(Arrays.asList(flight2));

        flightReservationController.getFlights(validParams);
        verify(flightReservationServiceMock, times(1)).getFlights(validParams);
        assertEquals(flightReservationController.getFlights(validParams).getStatusCode().value(), 200);
        assertEquals(flightReservationController.getFlights(withoutParams).getBody(), Arrays.asList(flight2));
    }

    @Test
    @DisplayName("getFlights with invalid params don't call to HotelReservationService")
    public void whenGetHotelsWithInvalidParamsThenCallZeroTimeToHotelReservationServiceAndThrowExceptionTest() throws IOException, ReservationException {
        Exception ex = assertThrows(InvalidQueryException.class, () -> flightReservationController.getFlights(invalidParams));
        verify(flightReservationServiceMock, times(0)).getFlights(invalidParams);
        assertEquals("Pedido inválido", ex.getMessage());
    }

    @Test
    @DisplayName("send flight reservation request with valid param")
    public void whenSendBookingRequestWithValidParamsThenCallOneTimeToHotelReservationServiceAndReturnReservationResponseTest() throws Exception {
        FlightReservationResponse response = new FlightReservationResponse();

        when(flightReservationServiceMock.sendFlightReservationRequest(reservation)).thenReturn(response);

        flightReservationController.sendFlightReservationRequest(reservation);
        verify(flightReservationServiceMock, times(1)).sendFlightReservationRequest(reservation);
        assertEquals(flightReservationController.sendFlightReservationRequest(reservation).getStatusCode().value(), 200);
        assertEquals(flightReservationController.sendFlightReservationRequest(reservation).getBody(), response);
    }

    @Test
    @DisplayName("send booking request with invalid param")
    public void whenSendBookingRequestWithInvalidParamsThenCallZeroTimeToHotelReservationServiceAndThrowExceptionTest() throws IOException, ReservationException {
        reservation = null;
        Exception ex = assertThrows(InvalidQueryException.class, () -> flightReservationController.sendFlightReservationRequest(reservation));
        verify(flightReservationServiceMock, times(0)).sendFlightReservationRequest(reservation);
        assertEquals("Tiene que ingresar un pedido de reserva", ex.getMessage());
    }
}