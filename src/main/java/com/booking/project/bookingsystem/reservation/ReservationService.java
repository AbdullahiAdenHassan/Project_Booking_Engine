package com.booking.project.bookingsystem.reservation;

import com.booking.project.bookingsystem.exception.ApiRequestException;
import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
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

    public int addNewReservation(Reservation reservation) {

        return addNewReservation(reservation.getReservation_id(), reservation);
    }

    public int addNewReservation(UUID reservation_id, Reservation reservation) {
        //Check if the random UUID already exists on the reservation table later work!
        UUID newReservationId = Optional.ofNullable(reservation_id)
                .orElse(UUID.randomUUID());

        if (reservationDataAccessService.isThatReservationDateForThatRoomTaken(reservation)) {
            throw new ApiRequestException("Room "+reservation.getHotel_room().getRoom()+" In "+reservation.getHotel_chain()+" between "+ reservation.getDate() + " is taken");
        }

        return reservationDataAccessService.insertReservation(newReservationId, reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDataAccessService.selectAllReservations();
    }

    public void deleteReservation(UUID reservation_id) {
        reservationDataAccessService.deleteReservationById(reservation_id);
    }

    public void updateReservation(UUID reservation_id, Reservation reservation) {

        Optional.ofNullable(reservation.getHotel_chain())
                .filter(hotel_chain -> !StringUtils.isEmpty(hotel_chain))
                .ifPresent(hotel_chain -> reservationDataAccessService.updateHotelChain(reservation_id, hotel_chain));

        Optional.of(reservation.getHotel_room().getRoom())
                .ifPresent(room_number -> reservationDataAccessService.updateHotelRoom(reservation_id, room_number));

        Optional.ofNullable(reservation.getGuest().getFirst_name())
                .filter(first_name -> !first_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(first_name -> reservationDataAccessService.updateFirstName(reservation_id, first_name));

        Optional.ofNullable(reservation.getGuest().getLast_name())
                .filter(last_name -> !last_name.isEmpty())
                .map(StringUtils::capitalize)
                .ifPresent(last_name -> reservationDataAccessService.updateLastName(reservation_id, last_name));

        Optional.ofNullable(reservation.getArrival_date())
                .filter(arrival_date -> arrival_date.isEqual(LocalDate.now()) || arrival_date.isAfter(LocalDate.now()))
                .ifPresent(arrival_date -> reservationDataAccessService.updateArrivalDate(reservation_id, arrival_date));

        Optional.ofNullable(reservation.getDeparture_date())
                .filter(departure_date -> departure_date.isEqual(LocalDate.now()) || departure_date.isAfter(LocalDate.now()))
                .ifPresent(departure_date -> reservationDataAccessService.updateDepartureDate(reservation_id, departure_date));

        //add email and mobil phone later!

    }

    public Reservation getReservation(UUID reservation_id) {
        return reservationDataAccessService.fetchReservationById(reservation_id);
    }

    List<Reservation> getSortedReservation(HotelChain hotel_chain) {
        return reservationDataAccessService.getSortedReservationByHotel(hotel_chain);
    }

    public void deleteAllReservation() {
        reservationDataAccessService.deleteAllReservation();
    }
}
