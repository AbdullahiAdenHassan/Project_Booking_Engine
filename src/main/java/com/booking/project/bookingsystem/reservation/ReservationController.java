package com.booking.project.bookingsystem.reservation;

import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "/getAllReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping(path = "/getAllReservation/{reservation_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable("reservation_id") UUID reservation_id) {
        return reservationService.getReservation(reservation_id);
    }

    @GetMapping(path = "/getAllReservation/{hotel_chain}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getSortedReservation(@PathVariable("hotel_chain") HotelChain hotel_chain) {
        return reservationService.getSortedReservation(hotel_chain);
    }

    @PostMapping(path = "/addReservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // Both xml and json later!
    public void addNewReservation(@RequestBody @Valid Reservation reservation) {
        reservationService.addNewReservation(reservation);
    }

    @PutMapping(path = "/updateReservation/{reservation_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateReservation(@PathVariable("reservation_id") UUID reservation_id, @RequestBody Reservation reservation) {
        reservationService.updateReservation(reservation_id, reservation);
    }

    @DeleteMapping(path = "/deleteAll/{reservation_id}")
    public void deleteReservation(@PathVariable("reservation_id") UUID reservation_id) {
        reservationService.deleteReservation(reservation_id);
    }
    @DeleteMapping(path = "/deleteAll")
    public void deleteAllReservation(){
        reservationService.deleteAllReservation();
    }


}
