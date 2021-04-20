package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String regex = "^(.+)@(.+)$";

    public static void validateEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new InvalidEmailException("Por favor ingrese un email válido");
        }
    }

}
