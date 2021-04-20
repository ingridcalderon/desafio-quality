package com.meli.desafioquality.service;

import com.meli.desafioquality.dto.DateDTO;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.dto.HotelReservationDTO;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.model.HotelBooking;
import com.meli.desafioquality.model.PaymentMethod;
import com.meli.desafioquality.model.Person;
import com.meli.desafioquality.repository.HotelReservationRepository;
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
public class HotelReservationServiceTests {
    @Mock
    private HotelReservationRepository hotelReservationRepositoryMock;
    private HotelDTO hotel1;
    private HotelDTO hotel2;
    private List<HotelDTO> hotels;
    private HotelReservationService hotelReservationService;
    private Map<String, String> withoutParams;
    private Map<String, String> validDestinationParam;
    private Map<String, String> invalidDestinationParam;
    private HotelReservationDTO reservation;

    @Before
    public void setUp() throws ReservationException {
        hotelReservationService = new HotelReservationServiceImpl(hotelReservationRepositoryMock);
        hotel1 = new HotelDTO("CH-0002", "Cataratas Hotel", "Puerto Iguazú", "Doble",
                "$6300", "10/02/2021", "20/03/2021", "NO");
        hotel2 = new HotelDTO("HB-0001", "Hotel Bristol", "Buenos Aires", "Single",
                "$5435", "10/02/2021", "19/03/2021", "NO");
        hotels = Arrays.asList(hotel1, hotel2);

        withoutParams = new HashMap<>();

        validDestinationParam = new HashMap<>();
        validDestinationParam.put("destination", "Buenos Aires");

        invalidDestinationParam = new HashMap<>();
        invalidDestinationParam.put("destination", "Zurich");

        Person person1 = new Person("12345678", "Pepito", "Suarez", new DateDTO("18/07/1990"), "pepitosuarez@gmail.com");
        Person person2 = new Person("87654321", "Pepita", "Suarez", new DateDTO("23/09/1992"), "pepitasuarez@gmail.com");
        List<Person> people = Arrays.asList(person1, person2);
        PaymentMethod paymentMethod = new PaymentMethod("DEBIT", "1234-5678-1234", 1);
        HotelBooking hotelBooking = new HotelBooking("12/02/2021", "14/02/2021", "Buenos Aires",
                "HB-0001", 2, "DOUBLE", people, paymentMethod);

        reservation = new HotelReservationDTO("ingrid.calderon@mercadolibre.com", hotelBooking);
    }

    @Test
    @DisplayName("get hotels without params")
    public void whenGetHotelsWithoutParamsThenCallOneTimeToHotelReservationRepositoryAndReturnAllHotelesTest() throws IOException, ReservationException {
        when(hotelReservationRepositoryMock.getHotels()).thenReturn(hotels);

        hotelReservationService.getHotels(withoutParams);

        verify(hotelReservationRepositoryMock, times(1)).getHotels();
        assertEquals(hotelReservationService.getHotels(withoutParams), hotels);
    }

    @Test
    @DisplayName("get hotels with valid params")
    public void whenGetHotelsWithValidParamsThenCallOneTimeToHotelReservationRepositoryTest() throws IOException, ReservationException {
        when(hotelReservationRepositoryMock.getHotels()).thenReturn(hotels);

        hotelReservationService.getHotels(validDestinationParam);

        verify(hotelReservationRepositoryMock, times(1)).getHotels();
        assertEquals(Arrays.asList(hotel2), hotelReservationService.getHotels(validDestinationParam));
    }

    @Test
    @DisplayName("get hotels with invalid params")
    public void whenGetHotelsWithInvalidParamsThenCallOneTimeToHotelReservationRepositoryAndThrowExceptionTest() throws IOException, ReservationException {
        when(hotelReservationRepositoryMock.getHotels()).thenReturn(hotels);
        Exception ex = assertThrows(ReservationException.class, () -> hotelReservationService.getHotels(invalidDestinationParam));

        verify(hotelReservationRepositoryMock, times(1)).getHotels();
        assertEquals("El destino elegido no existe", ex.getMessage());
    }

}