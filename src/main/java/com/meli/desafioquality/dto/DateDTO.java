package com.meli.desafioquality.dto;

import com.meli.desafioquality.exception.InvalidDateFormatException;
import com.meli.desafioquality.utils.validator.DateValidator;
import lombok.Data;

@Data
public class DateDTO {
    private String day;
    private String month;
    private String year;

    public DateDTO(String date) throws InvalidDateFormatException {
        if(DateValidator.isValid(date)){
            this.day = date.substring(0,2);
            this.month = date.substring(3,5);
            this.year = date.substring(6,10);
        }
    }

    @Override
    public String toString(){
        return this.getDay() + "/" + this.getMonth() + "/" +this.getYear();
    }
}
