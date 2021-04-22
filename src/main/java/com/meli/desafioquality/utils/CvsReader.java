package com.meli.desafioquality.utils;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.ReservationException;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CvsReader {
    public static final String SEPARATOR = ",";

    public static List<?> loadElements(String pathCvs, Class className)  {
        BufferedReader readBuffer = null;
        List<Object> elems = new ArrayList<>();

        try {
            File file = ResourceUtils.getFile(pathCvs);
            readBuffer = new BufferedReader(new FileReader(file));

            String line = readBuffer.readLine();
            boolean header = true;

            while (line != null) {
                String[] dataLine = line.split(SEPARATOR);
                List<String> args = Arrays.asList(dataLine);

                if(!header){
                    if(className == HotelDTO.class) {
                        HotelDTO hotel = new HotelDTO(args);
                        elems.add(hotel);
                    }
                    if(className == FlightDTO.class){
                        FlightDTO flight = new FlightDTO(args);
                        elems.add(flight);
                    }
                }

                header = false;
                line = readBuffer.readLine();
            }
        } catch (IOException | ReservationException e) {
            e.printStackTrace();
        } finally {
            if (readBuffer != null) {
                try {
                    readBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return elems;
    }
}