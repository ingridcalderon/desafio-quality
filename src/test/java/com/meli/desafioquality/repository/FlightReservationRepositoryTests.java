package com.meli.desafioquality.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightReservationRepositoryTests {
    private FlightReservationRepository flightReservationRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private FlightDTO flight;

    @Before
    public void setUp() throws ReservationException, ParseException {
        flightReservationRepository = new FlightReservationRepositoryImpl("src/test/resources/dbFlightsTest.csv");
        flight = new FlightDTO("BAPI-1235", "Buenos Aires", "Puerto Iguazú", "Economy", "$6.500",
                "10/02/2021", "15/02/2021", "NO");
    }

    @Test
    @DisplayName("get all hotels")
    public void getAllHotelsTest() throws IOException {
        List<FlightDTO> flightsMock = objectMapper.readValue(
                new File("src/test/resources/flightsMock.json"),
                new TypeReference<>() {
                });
        List<FlightDTO> flightsRepo = flightReservationRepository.getFlights();

        assertEquals(flightsRepo, flightsMock);
    }

    @Test
    @DisplayName("get hotel by code with valid code")
    public void whenGetHotelByCodeWithValidCodeThenReturnHotelTest() throws ReservationException {
        assertEquals(flight, flightReservationRepository.getFlightByCode("BAPI-1235"));
    }

    @Test
    @DisplayName("get hotel by code with invalid code")
    public void whenGetHotelWithInvalidCodeThrowExceptionTest() {
        Exception ex = assertThrows(InvalidQueryException.class, () -> flightReservationRepository.getFlightByCode("CH-0009"));
        assertEquals("Verifique el código de vuelo", ex.getMessage());
    }

    @Test
    @DisplayName("update by code if reserved")
    public void whenUpdateHotelByCodeThenIsReservedReturnTrueTest() throws ReservationException {
        assertFalse(flightReservationRepository.getFlightByCode("BAPI-1235").isReserved());
        flightReservationRepository.updateFlightByCode("BAPI-1235");
        assertTrue(flightReservationRepository.getFlightByCode("BAPI-1235").isReserved());
    }
}