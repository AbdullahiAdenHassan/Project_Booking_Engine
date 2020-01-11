package com.booking.project.bookingsystem.reservation.reservationservice.unittest;

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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceMockTest {

    @Mock
    ReservationDataAccessService reservationDataAccessService; //instead of ReservationDataAccessService = mock(ReservationDataAccessService.class);

    @InjectMocks
    ReservationService reservationServiceMock; // instead of ReservationService reservationServiceMock = new ReservationService (ReservationDataAccessService)

    private List<Reservation> allReservations;

    @Test
    public void test_inserting_one_reservation() {
        givenThatReservationIdIsNull();
        whenReservationIdIsAddedToReservation();
        thenVerifyReservationExistInReservationDb();
    }

    @Test
    public void test_inserting_multiple_reservations() {
        givenThatNoReservationExists();
        whenTwoReservationsIsAdded();
        thenVerifyMultipleReservationExists();
    }

    @Test
    public void test_delete_one_reservation() {
        givenThatThreeReservationExists();
        whenOneReservationIsDeletedById();
        thenVerifyThatReservationDoesNotExit();
    }

    @Test
    public void test_update_one_reservation() {
        givenThatTwoReservationsExists();
        whenReservationHasBeenUpdateById();
        thenVerifyThatReservationHasBeenUpdated();
    }

    @Test
    public void test_getting_one_reservation() {
        givenThatTwoReservationsExists();
        whenFetchingOneReservationById();
        thenVerifyThatReservationHasBeenRetrieved();
    }


    private void givenThatTwoReservationsExists() {
        Reservation reservation1 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bb"),
                HotelChain.Comfort_Hotel,
                101,
                "abdullahi",
                "hassan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        Reservation reservation2 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bc"),
                HotelChain.Comfort_Hotel,
                102,
                "ninos",
                "slewan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        List<Reservation> reservationList = constructReservationList(reservation1, reservation2);
        when(reservationDataAccessService.selectAllReservations()).thenReturn(reservationList);

        reservationServiceMock.addNewReservation(reservation1.getReservation_id(), reservation1);
        verify(reservationDataAccessService).insertReservation(reservation1.getReservation_id(),reservation1);

        reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2);
        verify(reservationDataAccessService).insertReservation(reservation2.getReservation_id(),reservation1);
    }

    private void whenReservationHasBeenUpdateById() {
        List<Reservation> collect = reservationDataAccessService
                .selectAllReservations()
                .stream()
                .filter(reservation -> reservation.getReservation_id().compareTo(UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bc")) != 0)
                .collect(Collectors.toList());

        collect.add(new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bc"),
                HotelChain.Quality_Hotel,
                101,
                "Adam",
                "Svensson",
                LocalDate.parse("2020-01-05"),
                LocalDate.parse("2020-01-08")));

        allReservations = collect;
        verify(reservationDataAccessService, times(1)).selectAllReservations();
        reservationServiceMock.updateReservation(allReservations.get(1).getReservation_id(), allReservations.get(1));
    }

    private void thenVerifyThatReservationHasBeenUpdated() {
        assertEquals(2, allReservations.size());
        assertEquals("46693067-8e23-4d07-a67a-cfa9f252f0bc", allReservations.get(1).getReservation_id().toString());
        assertEquals("Adam", allReservations.get(1).getGuest().getFirst_name());
        assertEquals("Svensson", allReservations.get(1).getGuest().getLast_name());
        allReservations.clear();
    }

    private void givenThatThreeReservationExists() {
        Reservation reservation1 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bb"),
                HotelChain.Comfort_Hotel,
                101,
                "abdullahi",
                "hassan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        Reservation reservation2 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bc"),
                HotelChain.Comfort_Hotel,
                102,
                "ninos",
                "slewan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        Reservation reservation3 = new Reservation(
                UUID.fromString("56693067-8e23-4d07-a67a-cfa9f252f0fa"),
                HotelChain.Comfort_Hotel,
                103,
                "jannis",
                "olsson",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        List<Reservation> reservationList = constructReservationList(reservation1, reservation2, reservation3);
        Mockito.when(reservationDataAccessService.selectAllReservations()).thenReturn(reservationList);
        reservationServiceMock.addNewReservation(reservation1.getReservation_id(), reservation1);
        reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2);
        reservationServiceMock.addNewReservation(reservation3.getReservation_id(), reservation3);
        reservationServiceMock.deleteReservation(reservation1.getReservation_id());
    }

    private void whenOneReservationIsDeletedById() {
        allReservations = reservationDataAccessService
                .selectAllReservations()
                .stream()
                .filter(reservation -> !reservation.getReservation_id().equals(UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bb")))
                .collect(Collectors.toList());

        verify(reservationDataAccessService, times(1)).selectAllReservations();
    }

    private void thenVerifyThatReservationDoesNotExit() {
        assertEquals(2, allReservations.size());
        assertNotEquals("46693067-8e23-4d07-a67a-cfa9f252f0bb", allReservations.get(0).getReservation_id());
        assertNotEquals("46693067-8e23-4d07-a67a-cfa9f252f0bb", allReservations.get(1).getReservation_id());
        allReservations.clear();
    }

    private void givenThatNoReservationExists() {
        Reservation reservation1 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bb"),
                HotelChain.Comfort_Hotel,
                101,
                "abdullahi",
                "hassan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        Reservation reservation2 = new Reservation(
                UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bc"),
                HotelChain.Comfort_Hotel,
                102,
                "ninos",
                "slewan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        List<Reservation> reservationList = constructReservationList(reservation1, reservation2);
        Mockito.when(reservationDataAccessService.selectAllReservations()).thenReturn(reservationList);
        reservationServiceMock.addNewReservation(reservation1.getReservation_id(), reservation1);
        verify(reservationDataAccessService).insertReservation(reservation1.getReservation_id(),reservation1);
        reservationServiceMock.addNewReservation(reservation2.getReservation_id(), reservation2);
        verify(reservationDataAccessService).insertReservation(reservation2.getReservation_id(),reservation2);
    }

    private void whenTwoReservationsIsAdded() {
        allReservations = reservationDataAccessService.selectAllReservations();
        verify(reservationDataAccessService, times(1)).selectAllReservations();
    }

    private void thenVerifyMultipleReservationExists() {
        assertEquals(2, allReservations.size());
        allReservations.clear();
    }

    private void givenThatReservationIdIsNull() {
        Reservation reservation1 = new Reservation(
                null,
                HotelChain.Comfort_Hotel,
                101,
                "abdullahi",
                "hassan",
                LocalDate.parse("2022-01-03"),
                LocalDate.parse("2022-01-05")
        );

        List<Reservation> reservationList = constructReservationList(reservation1); //assign statement to new local variable is ctrl + alt + v
        //Tell the mock object to return our known set of hardcoded reservations, when requested!
        Mockito.when(reservationDataAccessService.selectAllReservations()).thenReturn(reservationList);
        //associate the mock object with the object we are testing
        reservationServiceMock.addNewReservation(reservation1);
    }

    private void whenReservationIdIsAddedToReservation() {
        allReservations = reservationDataAccessService.selectAllReservations();
        verify(reservationDataAccessService, times(1)).selectAllReservations();
    }

    private void thenVerifyReservationExistInReservationDb() {
        assertEquals(1, allReservations.size());
        assertFalse(allReservations.isEmpty());
        allReservations.clear();
    }

    private void whenFetchingOneReservationById() {
        allReservations = reservationDataAccessService.selectAllReservations()
                .stream()
                .filter(reservation -> reservation.getReservation_id().equals(UUID.fromString("46693067-8e23-4d07-a67a-cfa9f252f0bb")))
                .collect(Collectors.toList());

        verify(reservationDataAccessService,times(1)).selectAllReservations();
        reservationServiceMock.getReservation(allReservations.get(0).getReservation_id());
    }

    private void thenVerifyThatReservationHasBeenRetrieved() {
        assertEquals(1,allReservations.size());
        assertEquals("46693067-8e23-4d07-a67a-cfa9f252f0bb",allReservations.get(0).getReservation_id().toString());
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
