package com.meli.desafioquality.utils;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.exception.InvalidDateFormatException;
import com.meli.desafioquality.exception.InvalidRoomTypeException;
import com.meli.desafioquality.dto.HotelDTO;
import com.meli.desafioquality.exception.ReservationException;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CvsReader {
    public static final String SEPARATOR = ",";

    public static List<HotelDTO> loadDataBase(String pathCvs)  {
        BufferedReader readBuffer = null;
        List<HotelDTO> hotels = new ArrayList<>();

        try {
            File file = ResourceUtils.getFile(pathCvs);
            readBuffer = new BufferedReader(new FileReader(file));

            String line = readBuffer.readLine();
            boolean header = true;

            while (line != null) {
                String[] dataLine = line.split(SEPARATOR);
                List<String> fixedLengthList = Arrays.asList(dataLine);
                ArrayList<String> listOfString = new ArrayList<>(fixedLengthList);

                if(!header){
                    HotelDTO hotelAux = new HotelDTO(listOfString.get(0),listOfString.get(1),listOfString.get(2),listOfString.get(3),listOfString.get(4),listOfString.get(5),listOfString.get(6),listOfString.get(7));
                    hotels.add(hotelAux);
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
        return hotels;
    }

    public static List<FlightDTO> loadFlightsDataBase(String pathCvs)  {
        BufferedReader readBuffer = null;
        List<FlightDTO> flights = new ArrayList<>();

        try {
            File file = ResourceUtils.getFile(pathCvs);
            readBuffer = new BufferedReader(new FileReader(file));

            String line = readBuffer.readLine();
            boolean header = true;

            while (line != null) {
                String[] dataLine = line.split(SEPARATOR);
                List<String> fixedLengthList = Arrays.asList(dataLine);
                ArrayList<String> listOfString = new ArrayList<>(fixedLengthList);

                if(!header){
                    FlightDTO flightAux = new FlightDTO(listOfString.get(0),listOfString.get(1),listOfString.get(2),
                            listOfString.get(3),listOfString.get(4),listOfString.get(5),listOfString.get(6),"NO");
                    flights.add(flightAux);
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
        return flights;
    }

}