package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidRoomTypeException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomTypeValidatorTests {
    @Test
    @DisplayName("Valid room type")
    public void whenRoomTypeIsValidReturnTrueTest() throws InvalidRoomTypeException {
        assertTrue(RoomTypeValidator.validRoomType("Doble").equals("Doble"));
    }

    @Test
    @DisplayName("Invalid room type")
    public void whenRoomTypeIsInvalidThrowExceptionTest() {
        Exception ex = assertThrows(InvalidRoomTypeException.class, () -> RoomTypeValidator.validRoomType("Doblesssss"));
        assertEquals("Tipo de habitación inválida", ex.getMessage());
    }

    @Test
    @DisplayName("Room can be booked")
    public void whenIWantToBookASingleRoomForOnePersonThenReturnTrueTest()  {
        assertTrue(RoomTypeValidator.canBeBookedForXPeople("SINGLE", 1));
    }

    @Test
    @DisplayName("Room can't be booked")
    public void whenIWantToBookASingleRoomForTwoPeopleThenReturnFalseTest()  {
        assertFalse(RoomTypeValidator.canBeBookedForXPeople("SINGLE", 2));
    }
}