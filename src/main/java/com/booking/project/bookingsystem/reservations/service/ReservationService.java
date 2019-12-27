package com.booking.project.bookingsystem.reservations.service;

import com.booking.project.bookingsystem.exception.ApiRequestException;
import com.booking.project.bookingsystem.reservations.dao.ReservationDataAccessService;
import com.booking.project.bookingsystem.reservations.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*Business logic*/
@Service
public class ReservationService {
    private final ReservationDataAccessService reservationDataAccessService;


    @Autowired
    public ReservationService(ReservationDataAccessService reservationDataAccessService) {
        this.reservationDataAccessService = reservationDataAccessService;
    }

    public void addNewReservation(Reservation reservation){
        addNewReservation(null,reservation);
    }

    void addNewReservation(UUID reservationId, Reservation reservation) {
        //add random UUID that is not on the reservation table later work!
        UUID newReservationId = Optional.ofNullable(reservationId)
                .orElse(UUID.randomUUID());

        if (reservationDataAccessService.isReservationDateTaken(reservation.getArrivalDate(),
                reservation.getDepartureDate())) {
            throw new ApiRequestException(reservation.getDate() + " is taken");
        }

        reservationDataAccessService.insertReservation(newReservationId, reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDataAccessService.selectAllReservations();
    }

    public void deleteReservation(UUID reservation_id) {
        reservationDataAccessService.deleteReservationById(reservation_id);
    }

    public void updateReservation(UUID reservation_id, Reservation reservation) {

        //maybe change to enum later!
        Optional.ofNullable(reservation.getHotel_brand())
                .filter(hotel_brand ->!StringUtils.isEmpty(hotel_brand))
                .map(StringUtils::capitalize)
                .ifPresent(hotel_brand ->reservationDataAccessService.updateHotelBrand(reservation_id, hotel_brand));

        Optional.ofNullable(reservation.getRoom().getRoom())
                .ifPresent( room_number -> reservationDataAccessService.updateHotelRoom(reservation_id, room_number));

        Optional.ofNullable(reservation.getGuest().getFirst_name())
                .filter(first_name ->!first_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(first_name -> reservationDataAccessService.updateFirstName(reservation_id, first_name));

        Optional.ofNullable(reservation.getGuest().getLast_name())
                .filter(last_name ->!last_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(last_name -> reservationDataAccessService.updateLastName(reservation_id, last_name));


        //add email and mobil phone later!

        // Email

        // Mobil phone

    }
}
