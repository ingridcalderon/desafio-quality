package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.InvalidPaymentMethodException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PaymentMethodValidator {
    private static final HashMap<String, List<Double>> paymentMethods = getPaymentMethods();

    private static HashMap<String, List<Double>> getPaymentMethods(){
        HashMap<String, List<Double>> payment = new HashMap<>();
        payment.put("DEBIT", Arrays.asList(0.0));
        payment.put("CREDIT", Arrays.asList(0.05, 0.05, 0.05, 0.1, 0.1, 0.1));

        return payment;
    }

    public static boolean validPayment(String type, int dues) throws InvalidPaymentMethodException {
        if(!paymentMethods.containsKey(type)){
            throw new InvalidPaymentMethodException("El método de pago es inválido");
        }

        if(!(paymentMethods.get(type).size() >= dues)){
            throw new InvalidPaymentMethodException("No se admite esta cantidad de cuotas con el método de pago elegido");
        }

        return true;
    }

    public static double getInterest(String type, int dues) {
        return paymentMethods.get(type).get(dues-1);
    }
}
