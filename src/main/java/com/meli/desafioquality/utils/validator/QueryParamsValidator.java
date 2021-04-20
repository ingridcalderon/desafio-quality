package com.meli.desafioquality.utils.validator;

import com.meli.desafioquality.enums.FilterEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryParamsValidator {

    public static boolean validQuery(Map<String, String> queryParams){
        List<String> listParams = new ArrayList<>(queryParams.keySet());

        return validateAll(listParams) && hasValidateDates(listParams);
    }

    public static boolean validateAll(List<String> listParams){
        return listParams.stream().filter(QueryParamsValidator::validFilter).count() == listParams.size();
    }

    private static boolean validFilter(String filter){
        return filter.equals(FilterEnum.DATE_FROM.getDescription()) ||
                filter.equals(FilterEnum.DATE_TO.getDescription()) ||
                filter.equals(FilterEnum.DESTINATION.getDescription());
    }

    private static boolean hasValidateDates(List<String> listParams){
        boolean dateFromEntered = listParams.stream().filter(f -> f.equals(FilterEnum.DATE_FROM.getDescription())).count() == 1;
        boolean dateToEntered = listParams.stream().filter(f -> f.equals(FilterEnum.DATE_TO.getDescription())).count() == 1;

        if(!dateFromEntered && !dateToEntered){
            return true;
        } else {
            return dateFromEntered && dateToEntered;
        }
    }

}
