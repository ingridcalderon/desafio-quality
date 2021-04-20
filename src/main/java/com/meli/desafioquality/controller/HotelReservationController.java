package com.meli.desafioquality.controller;

import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.dto.HotelReservationDTO;
import com.meli.desafioquality.dto.StatusCodeDTO;
import com.meli.desafioquality.service.HotelReservationService;
import com.meli.desafioquality.utils.validator.QueryParamsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelReservationController {

    private final HotelReservationService hotelReservationService;

    public HotelReservationController(HotelReservationService hotelReservationService) {
        this.hotelReservationService = hotelReservationService;
    }

    @GetMapping("/hotels")
    public ResponseEntity getHotels(@RequestParam Map<String, String> queryParams) throws ReservationException, FileNotFoundException {
        if (!QueryParamsValidator.validQuery(queryParams)) {
            throw new InvalidQueryException("Pedido inválido");
        }
        return ResponseEntity.ok(hotelReservationService.getHotels(queryParams));
    }

    @PostMapping("/booking")
    public ResponseEntity sendBookingRequest(@RequestBody (required = false) HotelReservationDTO bookingRequest) throws Exception {
        if(bookingRequest == null){
            throw new InvalidQueryException("Tiene que ingresar un pedido de reserva");
        }
        return ResponseEntity.ok(hotelReservationService.sendBookingRequest(bookingRequest));
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity handlerException(ReservationException e){
        StatusCodeDTO statusDTO = new StatusCodeDTO(e.getCode(), e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.valueOf(e.getCode()));
    }
}