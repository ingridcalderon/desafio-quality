package com.meli.desafioquality.repository;

import com.meli.desafioquality.dto.FlightDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.utils.CvsReader;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class FlightReservationRepositoryImpl implements FlightReservationRepository {

    private final List<FlightDTO> database;

    public FlightReservationRepositoryImpl(@Value("${path.flights:dbVuelos.csv}") String path) throws ReservationException, ParseException {
        this.database = (List<FlightDTO>) CvsReader.loadElements(path, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlights() {
        return this.getDatabase();
    }

    @Override
    public FlightDTO getFlightByCode(String code) throws InvalidQueryException {
        Optional<FlightDTO> flight = this.getDatabase().stream().filter(f -> f.getFlightNumber().equals(code)).findFirst();
        if(!flight.isPresent()){
            throw new InvalidQueryException("Verifique el código de vuelo");
        }
        return flight.get();
    }

    @Override
    public void updateFlightByCode(String code) {
        for(FlightDTO flight : this.getDatabase()){
            if(flight.getFlightNumber().equals(code)){
                flight.setReserved(true);
            }
        }
    }
}
