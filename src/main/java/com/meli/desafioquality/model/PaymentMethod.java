package com.meli.desafioquality.model;

import com.meli.desafioquality.exception.InvalidPaymentMethodException;
import com.meli.desafioquality.utils.validator.PaymentMethodValidator;
import lombok.Data;

@Data
public class PaymentMethod {
    private String type;
    private String number;
    private int dues;

    public PaymentMethod(String type, String number, int dues) throws InvalidPaymentMethodException {
        if(PaymentMethodValidator.validPayment(type, dues)){
            this.type = type;
            this.number = number;
            this.dues = dues;
        }
    }
}
