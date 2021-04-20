package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidEmailException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTests {
    @Mock
    EmailValidator emailValidator;
    private String invalidEmail;

    @Before
    public void setUp(){
        invalidEmail = "ingrid.calderon.mercadolibre.com";
    }

    @Test(expected = InvalidEmailException.class)
    @DisplayName("Validate invalid email")
    public void whenValidAnInvalidEmailReturnThrowExceptionTest() throws InvalidEmailException {
        doThrow().when(emailValidator).validateEmail(invalidEmail);
    }
}
