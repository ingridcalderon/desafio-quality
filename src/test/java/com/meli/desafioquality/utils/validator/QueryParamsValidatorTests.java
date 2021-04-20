package com.meli.desafioquality.utils.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryParamsValidatorTests {
    private Map<String, String> validParams;
    private Map<String, String> invalidParams;
    private Map<String, String> validDatesParams;
    private Map<String, String> invalidDatesParams;

    @Before
    public void setUp(){
        validParams = new HashMap<>();
        validParams.put("dateFrom", "");
        validParams.put("dateTo", "");
        validParams.put("destination", "");

        invalidParams = new HashMap<>();
        invalidParams.put("cualquierFiltro", "");

        validDatesParams = new HashMap<>();
        validDatesParams.put("dateFrom", "12/02/2021");
        validDatesParams.put("dateTo", "23/02/2021");

        invalidDatesParams = new HashMap<>();
        invalidDatesParams.put("dateFrom", "09/09/2021");
        invalidDatesParams.put("destination", "Geneve");
    }

    @Test
    @DisplayName("Validate without params")
    public void whenValidateEmptyParamsThenReturnTrueTest(){
        assertTrue(QueryParamsValidator.validQuery(new HashMap<>()));
    }

    @Test
    @DisplayName("Validate valid params")
    public void whenValidateValidParamsThenReturnTrueTest(){
        assertTrue(QueryParamsValidator.validQuery(validParams));
    }

    @Test
    @DisplayName("Validate invalid params")
    public void whenValidateInvalidParamsThenReturnFalseTest(){
        assertFalse(QueryParamsValidator.validQuery(invalidParams));
    }

    @Test
    @DisplayName("Validate dates params")
    public void whenAddADateParamThenCheckIfAddedTwoDatesAndReturnTrueTest(){
        assertTrue(QueryParamsValidator.validQuery(validDatesParams));
    }

    @Test
    @DisplayName("Validate invalid date param")
    public void whenAddADateParamThenCheckIfAddedTwoDatesAndReturnFalseTest(){
        assertFalse(QueryParamsValidator.validQuery(invalidDatesParams));
    }
}