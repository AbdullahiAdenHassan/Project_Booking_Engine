package com.booking.project.bookingsystem.reservations;

import com.booking.project.bookingsystem.exception.ApiRequestException;
import com.booking.project.bookingsystem.reservations.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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

    public void addNewReservation(Reservation reservation) {
        addNewReservation(null, reservation);
    }

    void addNewReservation(UUID reservation_id, Reservation reservation) {
        //Check if the random UUID already exists on the reservation table later work!
        UUID newReservationId = Optional.ofNullable(reservation_id)
                .orElse(UUID.randomUUID());

        if (reservationDataAccessService.isReservationDateTaken(reservation.getArrivalDate(),
                reservation.getDepartureDate())) {
            throw new ApiRequestException(reservation.getDate() + " is taken");
        }

        if(reservationDataAccessService.isReservationDateBetweenReservations(reservation_id, reservation)){
            throw new ApiRequestException(reservation.getDate() + " is between reservations");
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

        Optional.ofNullable(reservation.getHotelChain())
                .filter(hotel_chain -> !StringUtils.isEmpty(hotel_chain))
                .ifPresent(hotel_chain -> reservationDataAccessService.updateHotelChain(reservation_id, hotel_chain));

        Optional.of(reservation.getRoom().getRoom())
                .ifPresent(room_number -> reservationDataAccessService.updateHotelRoom(reservation_id, room_number));

        Optional.ofNullable(reservation.getGuest().getFirst_name())
                .filter(first_name -> !first_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(first_name -> reservationDataAccessService.updateFirstName(reservation_id, first_name));

        Optional.ofNullable(reservation.getGuest().getLast_name())
                .filter(last_name -> !last_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(last_name -> reservationDataAccessService.updateLastName(reservation_id, last_name));

        Optional.ofNullable(reservation.getArrivalDate())
                .filter(arrival_date -> arrival_date.isEqual(LocalDate.now()) || arrival_date.isAfter(LocalDate.now()))
                .ifPresent(arrival_date -> reservationDataAccessService.updateArrivalDate(reservation_id, arrival_date));

        Optional.ofNullable(reservation.getDepartureDate())
                .filter(departure_date -> departure_date.isEqual(LocalDate.now()) || departure_date.isAfter(LocalDate.now()))
                .ifPresent(departure_date -> reservationDataAccessService.updateDepartureDate(reservation_id, departure_date));

        //add email and mobil phone later!

    }
}
