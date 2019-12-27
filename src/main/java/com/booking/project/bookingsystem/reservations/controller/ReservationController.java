package com.booking.project.bookingsystem.reservations.controller;

import com.booking.project.bookingsystem.reservations.reservation.Reservation;
import com.booking.project.bookingsystem.reservations.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    // Insert into reservation table in database, annotation
    @PostMapping
    public void addNewReservation(@RequestBody @Valid Reservation reservation){
        reservationService.addNewReservation(reservation);
    }

    // Update reservation table in database, annotation
    @PutMapping (path = "/{reservation_id}") // To update with reservation_id go to localhost:8080/api/reservation_id and enter new values in postman
    public void updateReservation(@PathVariable ("reservation_id") UUID reservation_id, @RequestBody Reservation reservation){
        reservationService.updateReservation(reservation_id, reservation);
    }

    // Delete reservation table in database, annotation
    @DeleteMapping(path = "/{reservation_id}") // To delete with reservation_id go to localhost:8080/api/reservation_id
    public void deleteReservation(@PathVariable ("reservation_id") UUID reservation_id){
        reservationService.deleteReservation(reservation_id);
    }


}
