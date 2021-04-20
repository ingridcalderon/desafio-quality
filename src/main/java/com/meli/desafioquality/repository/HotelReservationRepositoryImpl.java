package com.meli.desafioquality.repository;

import com.meli.desafioquality.dto.HotelDTO;
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
public class HotelReservationRepositoryImpl implements HotelReservationRepository {

    private final List<HotelDTO> database;

    public HotelReservationRepositoryImpl(@Value("${path.hotels:dbHoteles.csv}") String path)  {
        this.database = CvsReader.loadDataBase(path);
    }

    @Override
    public List<HotelDTO> getHotels() {
        return this.getDatabase();
    }

    @Override
    public HotelDTO getHotelByCode(String code) throws InvalidQueryException {
        Optional<HotelDTO> hotel = this.getDatabase().stream().filter(h -> h.getHotelCode().equals(code)).findFirst();
        if(!hotel.isPresent()){
            throw new InvalidQueryException("Verifique el código de hotel");
        }
        return hotel.get();
    }

    @Override
    public void updateHotelByCode(String code) {
        for(HotelDTO hotel : this.getDatabase()){
            if(hotel.getHotelCode().equals(code)){
                hotel.setReserved(true);
            }
        }
    }
}
