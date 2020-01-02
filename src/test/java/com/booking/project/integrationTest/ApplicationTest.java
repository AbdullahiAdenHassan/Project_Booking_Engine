package com.booking.project.integrationTest;

import com.booking.project.bookingsystem.reservations.ReservationController;
import com.booking.project.bookingsystem.reservations.ReservationDataAccessService;
import com.booking.project.bookingsystem.reservations.model.Reservation;
import com.booking.project.bookingsystem.reservations.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class ApplicationTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private ReservationDataAccessService reservationDataAccessService;
    private ReservationService reservationService;
    private ReservationController reservationController;


    @Test
    public void itShouldAddReservations() {

/*
        Reservation reservation = new Reservation(UUID.randomUUID(),
                "Comfort_Hotel",
                101,
                "Abdullahi",
                "Aden Hassan",
                LocalDate.now().plusDays(12),
                LocalDate.now().plusDays(13));

        reservationController.addNewReservation(reservation);
        List<Reservation> reservationList = reservationController.getAllReservations();

        assertEquals(3 ,reservationList.size());*/
    }
}
