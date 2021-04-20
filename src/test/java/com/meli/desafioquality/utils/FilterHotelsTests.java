package com.meli.desafioquality.utils;

import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.NoFoundHotelsException;
import com.meli.desafioquality.exception.ReservationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FilterHotelsTests {
    private HotelDTO hotel1;
    private HotelDTO hotel2;
    private List<HotelDTO> hotels;

    @Before
    public void setUp(){
        hotel1 = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble",
                6300, "10/02/2021", "20/03/2021", false);
        hotel2 = new HotelDTO("BH-0002", "Hotel Bristol 2", "Buenos Aires", "Doble",
                7200, "12/04/2021", "17/04/2021", false);
        hotels = Arrays.asList(hotel1, hotel2);
    }

    @Test
    @DisplayName("Filter by valid destination")
    public void whenFilterByDestinationThenReturnHotelsTest() throws ReservationException {
        List<HotelDTO> hotelsAux = FilterHotels.byDestination(hotels, "Buenos Aires");
        assertEquals(1, hotelsAux.size());
        assertTrue(hotelsAux.contains(hotel2));
    }

    @Test
    @DisplayName("Filter by invalid destination")
    public void whenFilterByInvalidDestinationThenThrowExceptionTest(){
        Exception ex = assertThrows(NoFoundHotelsException.class, () -> FilterHotels.byDestination(hotels, "Roma"));
        assertEquals("El destino elegido no existe", ex.getMessage());
    }

    @Test
    @DisplayName("Filter by date")
    public void whenFilterByDateThenReturnHotelsNotReservedTest() throws ReservationException {
        List<HotelDTO> hotelsAux = FilterHotels.byDate("12/02/2021", "20/02/2021", hotels);
        assertEquals(1, hotelsAux.size());
        assertTrue(hotelsAux.contains(hotel1));
    }

    @Test
    @DisplayName("Filter by date and get no result")
    public void whenFilterByDateAndGetNoResultThenThrowExceptionTest(){
        Exception ex = assertThrows(NoFoundHotelsException.class, () -> FilterHotels.byDate("12/09/2021", "20/09/2021", hotels));
        assertEquals("No se encontró ningún hotel para la fecha elegida", ex.getMessage());
    }
}