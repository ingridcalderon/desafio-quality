package com.meli.desafioquality.enums;

public enum FilterEnum {
    DATE_FROM("dateFrom"),
    DATE_TO("dateTo"),
    DESTINATION("destination");

    private final String description;

    FilterEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
