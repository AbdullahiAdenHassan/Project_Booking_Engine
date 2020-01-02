package com.booking.project.bookingsystem.reservations;

import com.booking.project.bookingsystem.reservations.model.Reservation;
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

    @PostMapping
    public void addNewReservation(@RequestBody @Valid Reservation reservation){
        reservationService.addNewReservation(reservation);
    }

    @PutMapping (path = "/{reservation_id}")
    public void updateReservation(@PathVariable ("reservation_id") UUID reservation_id, @RequestBody Reservation reservation){
        reservationService.updateReservation(reservation_id, reservation);
    }

    @DeleteMapping(path = "/{reservation_id}")
    public void deleteReservation(@PathVariable ("reservation_id") UUID reservation_id){
        reservationService.deleteReservation(reservation_id);
    }


}
