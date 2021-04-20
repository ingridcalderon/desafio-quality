package com.meli.desafioquality.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class HotelReservationRepositoryTests {
    private HotelReservationRepository hotelReservationRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private HotelDTO hotel;

    @Before
    public void setUp() throws ReservationException {
        hotelReservationRepository = new HotelReservationRepositoryImpl("src/test/resources/dbHotelsTest.csv");
        hotel = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble",
                6300, "10/02/2021", "20/03/2021", false);
    }

    @Test
    @DisplayName("get all hotels")
    public void getAllHotelsTest() throws IOException {
        List<HotelDTO> hotelsMock = objectMapper.readValue(
                new File("src/test/resources/hotelsMock.json"),
                new TypeReference<>() {
                });
        List<HotelDTO> hotelsRepo = hotelReservationRepository.getHotels();

        assertEquals(hotelsRepo, hotelsMock);
    }

    @Test
    @DisplayName("get hotel by code with valid code")
    public void whenGetHotelByCodeWithValidCodeThenReturnHotelTest() throws ReservationException {
        assertEquals(hotel, hotelReservationRepository.getHotelByCode("CH-0002"));
    }

    @Test
    @DisplayName("get hotel by code with invalid code")
    public void whenGetHotelWithInvalidCodeThrowExceptionTest() {
        Exception ex = assertThrows(InvalidQueryException.class, () -> hotelReservationRepository.getHotelByCode("CH-0009"));
        assertEquals("Verifique el código de hotel", ex.getMessage());
    }

    @Test
    @DisplayName("update by code if reserved")
    public void whenUpdateHotelByCodeThenIsReservedReturnTrueTest() throws ReservationException {
        assertFalse(hotelReservationRepository.getHotelByCode("CH-0002").isReserved());
        hotelReservationRepository.updateHotelByCode("CH-0002");
        assertTrue(hotelReservationRepository.getHotelByCode("CH-0002").isReserved());
    }
}