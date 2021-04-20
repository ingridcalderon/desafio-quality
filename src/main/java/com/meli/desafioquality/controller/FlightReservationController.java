package com.meli.desafioquality.controller;

import com.meli.desafioquality.dto.FlightReservationDTO;
import com.meli.desafioquality.dto.StatusCodeDTO;
import com.meli.desafioquality.exception.InvalidQueryException;
import com.meli.desafioquality.exception.ReservationException;
import com.meli.desafioquality.model.FlightReservationResponse;
import com.meli.desafioquality.service.FlightReservationService;
import com.meli.desafioquality.utils.validator.QueryParamsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class FlightReservationController {

    private final FlightReservationService flightReservationService;

    public FlightReservationController(FlightReservationService flightReservationService) {
        this.flightReservationService = flightReservationService;
    }

    @GetMapping("/flights")
    public ResponseEntity getFlights(@RequestParam Map<String, String> queryParams) throws ReservationException, FileNotFoundException {
        if (!QueryParamsValidator.validQuery(queryParams)) {
            throw new InvalidQueryException("Pedido inválido");
        }
        return ResponseEntity.ok(flightReservationService.getFlights(queryParams));
    }

    @PostMapping("/flight-reservation")
    public ResponseEntity sendFlightReservationRequest(@RequestBody (required = false) FlightReservationDTO flightReservationRequest) throws Exception {
        if(flightReservationRequest == null){
            throw new InvalidQueryException("Tiene que ingresar un pedido de reserva");
        }
        return ResponseEntity.ok(flightReservationService.sendFlightReservationRequest(flightReservationRequest));
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity handlerException(ReservationException e){
        StatusCodeDTO statusDTO = new StatusCodeDTO(e.getCode(), e.getMessage());
        return new ResponseEntity(statusDTO, HttpStatus.valueOf(e.getCode()));
    }
}