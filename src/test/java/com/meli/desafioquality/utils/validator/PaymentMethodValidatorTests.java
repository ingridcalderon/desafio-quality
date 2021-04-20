package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidPaymentMethodException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PaymentMethodValidatorTests {
    @Test
    @DisplayName("Validate valid payment method")
    public void whenPaymentMethodAndDuesAreValidsReturnTrueTest() throws InvalidPaymentMethodException {
        assertTrue(PaymentMethodValidator.validPayment("DEBIT", 1));
    }

    @Test
    @DisplayName("Validate invalid dues of credit")
    public void whenDuesAreInvalidsThenThrowExceptionTest()  {
        Exception ex = assertThrows(InvalidPaymentMethodException.class, () -> PaymentMethodValidator.validPayment("CREDIT", 7));
        assertEquals("No se admite esta cantidad de cuotas con el método de pago elegido", ex.getMessage());
    }

    @Test
    @DisplayName("get valid payment interest (credit)")
    public void whenGetInterestOfPaymentMethodReturnADoubleTest() {
        assertEquals(0.1, PaymentMethodValidator.getInterest("CREDIT", 6));
    }

    @Test
    @DisplayName("get valid payment interest (debit)")
    public void whenGetInterestOfDebitPaymentMethodReturnZeroTest() {
        assertEquals(0.0, PaymentMethodValidator.getInterest("DEBIT", 1));
    }
}