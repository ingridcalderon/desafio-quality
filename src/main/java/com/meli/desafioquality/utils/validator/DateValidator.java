package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.exception.DepartureDateGreaterArrivalDateException;
import com.meli.desafioquality.exception.InvalidDateFormatException;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class DateValidator {

    public static boolean isValid(String date) throws InvalidDateFormatException {
        String day = date.substring(0,2);
        String month = date.substring(3,5);
        String year = date.substring(6,10);

        try{
            new LocalDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        } catch (Exception e){
            throw new InvalidDateFormatException("El formato de fecha debe ser dd/mm/aaaa");
        }

        return true;
    }

    public static boolean validateDates(String dateFrom, String dateTo) throws InvalidDateFormatException, DepartureDateGreaterArrivalDateException {
        return DateValidator.isValid(dateFrom) &&
                DateValidator.isValid(dateTo) &&
                DateValidator.dateLessThanOtherDate(dateFrom, dateTo);
    }

    private static boolean dateLessThanOtherDate(String dateFrom, String dateTo) throws DepartureDateGreaterArrivalDateException {
        LocalDate d1 = new LocalDate(Integer.parseInt(dateFrom.substring(6,10)), Integer.parseInt(dateFrom.substring(3,5)), Integer.parseInt(dateFrom.substring(0,2)));
        LocalDate d2 = new LocalDate(Integer.parseInt(dateTo.substring(6,10)), Integer.parseInt(dateTo.substring(3,5)), Integer.parseInt(dateTo.substring(0,2)));

        if(!d1.isBefore(d2)){
            throw new DepartureDateGreaterArrivalDateException("La fecha de entrada debe ser menor que la fecha de salida");
        }
        return true;

    }

    public static boolean dateLessThanOtherDate2(String dateFrom, String dateTo){
        LocalDate d1 = new LocalDate(Integer.parseInt(dateFrom.substring(6,10)), Integer.parseInt(dateFrom.substring(3,5)), Integer.parseInt(dateFrom.substring(0,2)));
        LocalDate d2 = new LocalDate(Integer.parseInt(dateTo.substring(6,10)), Integer.parseInt(dateTo.substring(3,5)), Integer.parseInt(dateTo.substring(0,2)));

        return d1.isBefore(d2);

    }

    public static int daysBetween(String dateFrom, String dateTo){
        LocalDate d1 = new LocalDate(Integer.parseInt(dateFrom.substring(6,10)), Integer.parseInt(dateFrom.substring(3,5)), Integer.parseInt(dateFrom.substring(0,2)));
        LocalDate d2 = new LocalDate(Integer.parseInt(dateTo.substring(6,10)), Integer.parseInt(dateTo.substring(3,5)), Integer.parseInt(dateTo.substring(0,2)));

        return Days.daysBetween(d1, d2).getDays();

    }
}
