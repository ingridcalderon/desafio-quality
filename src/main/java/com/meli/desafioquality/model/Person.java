package com.meli.desafioquality.model;

import com.meli.desafioquality.dto.DateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String dni;
    private String name;
    private String lastName;
    private DateDTO birthDate;
    private String mail;
}
