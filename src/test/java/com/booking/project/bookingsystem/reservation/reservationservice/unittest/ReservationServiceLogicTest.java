package com.booking.project.bookingsystem.reservation.reservationservice.unittest;


import com.booking.project.bookingsystem.exception.ApiRequestException;
import com.booking.project.bookingsystem.reservation.ReservationDataAccessService;
import com.booking.project.bookingsystem.reservation.ReservationService;
import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReservationServiceLogicTest {

    @Mock
    ReservationDataAccessService reservationDataAccessService;

    @InjectMocks
    ReservationService reservationServiceMock;

    private List<Reservation> reservationList;

    @Test
    public void you_should_not_be_able_to_book_same_reservation_twice() {

        Reservation reservation1 = new Reservation(
                UUID.randomUUID(),
                HotelChain.Comfort_Hotel,
                101,
                "Abdullahi",
                "Hassan",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        Reservation reservation2 = new Reservation(
                UUID.randomUUID(),
                HotelChain.Comfort_Hotel,
                101,
                "Abdullahi",
                "Hassan",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        reservationServiceMock.addNewReservation(reservation1.getReservation_id(), reservation1);
        verify(reservationDataAccessService).insertReservation(reservation1.getReservation_id(), reservation1);

        Mockito.when(reservationDataAccessService.selectAllReservations())
                .thenReturn(constructReservationList(reservation1));


        reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2);
        Mockito.when(reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2))
                .thenThrow(ApiRequestException.class);

        reservationList = reservationDataAccessService.selectAllReservations();

        assertEquals(1, reservationList.size());
        assertThrows(ApiRequestException.class, () ->
                reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2));

    }


    @Test
    public void you_should_not_be_able_to_book_same_room_twice_on_the_same_day() {

    }

    @Test
    public void you_should_not_be_able_too_book_a_room_in_the_middle_of_a_booked_date() {


    }

    private boolean verifyReservation(Reservation reservation) {

        boolean sameHotelRoom = reservationDataAccessService.selectAllReservations().stream()
                .anyMatch(reservationList -> reservationList.getHotel_chain().equals(reservation.getHotel_chain())
                        && reservationList.getHotel_room().equals(reservation.getHotel_room()));



        return !sameHotelRoom;
    }


    private List<Reservation> constructReservationList(Reservation... reservations) {
        List<Reservation> allReservations = Arrays
                .stream(reservations)
                .map(reservation -> {
                    UUID newReservationId = Optional.ofNullable(reservation.getReservation_id()).orElse(UUID.randomUUID());
                    return new Reservation(newReservationId,
                            reservation.getHotel_chain(),
                            reservation.getHotel_room().getRoom(),
                            reservation.getGuest().getFirst_name(),
                            reservation.getGuest().getLast_name(),
                            reservation.getArrival_date(),
                            reservation.getDeparture_date());
                })
                .collect(Collectors.toList());

        return allReservations;
    }

}
