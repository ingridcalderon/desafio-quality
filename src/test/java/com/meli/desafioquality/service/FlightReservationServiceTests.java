package com.meli.desafioquality.service;

import com.meli.desafioquality.dto.*;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.model.FlightReservation;
import com.meli.desafioquality.model.HotelBooking;
import com.meli.desafioquality.model.PaymentMethod;
import com.meli.desafioquality.model.Person;
import com.meli.desafioquality.repository.FlightReservationRepository;
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
public class FlightReservationServiceTests {
    @Mock
    private FlightReservationRepository flightReservationRepositoryMock;
    private FlightDTO flight1;
    private FlightDTO flight2;
    private List<FlightDTO> flights;
    private FlightReservationService flightReservationService;
    private Map<String, String> withoutParams;
    private Map<String, String> validDestinationParam;
    private Map<String, String> invalidDestinationParam;
    private FlightReservationDTO reservation;

    @Before
    public void setUp() throws ReservationException {
        flightReservationService = new FlightReservationServiceImpl(flightReservationRepositoryMock);
        flight1 = new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", "$6.500",
                "10/02/2021", "15/02/2021", "NO");
        flight2 = new FlightDTO("PIBA-1420", "Puerto Iguazú", "Bogotá", "Business", "$43.200",
                "10/02/2021", "20/02/2021", "NO");
        flights = Arrays.asList(flight1, flight2);

        withoutParams = new HashMap<>();

        validDestinationParam = new HashMap<>();
        validDestinationParam.put("destination", "Bogotá");

        invalidDestinationParam = new HashMap<>();
        invalidDestinationParam.put("destination", "Zurich");

        Person person1 = new Person("12345678", "Pepito", "Suarez", new DateDTO("18/07/1990"), "pepitosuarez@gmail.com");
        Person person2 = new Person("87654321", "Pepita", "Suarez", new DateDTO("23/09/1992"), "pepitasuarez@gmail.com");
        List<Person> people = Arrays.asList(person1, person2);
        PaymentMethod paymentMethod = new PaymentMethod("DEBIT", "1234-5678-1234", 1);
        FlightReservation flightReservation = new FlightReservation("12/02/2021", "14/02/2021", "Buenos Aires", "Puerto Iguazú",
                "HB-0001", 2, "ECONOMY", people, paymentMethod);

        reservation = new FlightReservationDTO("ingrid.calderon@mercadolibre.com", flightReservation);
    }

    @Test
    @DisplayName("get hotels without params")
    public void whenGetHotelsWithoutParamsThenCallOneTimeToHotelReservationRepositoryAndReturnAllHotelesTest() throws IOException, ReservationException {
        when(flightReservationRepositoryMock.getFlights()).thenReturn(flights);

        flightReservationService.getFlights(withoutParams);

        verify(flightReservationRepositoryMock, times(1)).getFlights();
        assertEquals(flightReservationService.getFlights(withoutParams), flights);
    }

    @Test
    @DisplayName("get hotels with valid params")
    public void whenGetHotelsWithValidParamsThenCallOneTimeToHotelReservationRepositoryTest() throws IOException, ReservationException {
        when(flightReservationRepositoryMock.getFlights()).thenReturn(flights);

        flightReservationService.getFlights(validDestinationParam);

        verify(flightReservationRepositoryMock, times(1)).getFlights();
        assertEquals(Arrays.asList(flight2), flightReservationService.getFlights(validDestinationParam));
    }

}