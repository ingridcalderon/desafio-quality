package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.DepartureDateGreaterArrivalDateException;
import com.meli.desafioquality.exception.InvalidDateFormatException;
import com.meli.desafioquality.exception.ReservationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DateValidatorTests {
    private String validDateFrom;
    private String validDateTo;
    private String invalidDate;
    private String invalidDateFrom;
    private String invalidDateTo;

    @Before
    public void setUp(){
        validDateFrom = "10/02/2021";
        validDateTo = "20/02/2021";
        invalidDate = "30/02/2021";
        invalidDateFrom = "28/05/2021";
        invalidDateTo = "17/05/2021";
    }

    @Test
    @DisplayName("Validate valid date")
    public void whenValidAStringLikeDateAndIsValidThenReturnTrueTest() throws InvalidDateFormatException {
        assertTrue(DateValidator.isValid(validDateFrom));
    }

    @Test
    @DisplayName("Validate invalid date")
    public void whenValidAStringLikeDateAndIsInvalidThrowExceptionTest(){
        Exception ex = assertThrows(InvalidDateFormatException.class, () -> DateValidator.isValid(invalidDate));
        assertEquals("El formato de fecha debe ser dd/mm/aaaa", ex.getMessage());
    }

    @Test
    @DisplayName("Validate two dates")
    public void whenValidTwoStringsLikeDateAndAreValidThenReturnTrueTest() throws ReservationException {
        assertTrue(DateValidator.validateDates(validDateFrom, validDateTo));
    }

    @Test
    @DisplayName("dateFrom greater dateTo")
    public void whenValidTwoStringsLikeDateAndDateFromIsGreaterThanDateToThrowExceptionTest(){
        Exception ex = assertThrows(DepartureDateGreaterArrivalDateException.class, () -> DateValidator.validateDates(invalidDateFrom, invalidDateTo));
        assertEquals("La fecha de entrada debe ser menor que la fecha de salida", ex.getMessage());
    }

    @Test
    @DisplayName("Count days between two dates")
    public void whenCountDaysBetweenTwoDatesThenReturnAIntTest() {
        assertEquals(10, DateValidator.daysBetween(validDateFrom, validDateTo));
    }
}