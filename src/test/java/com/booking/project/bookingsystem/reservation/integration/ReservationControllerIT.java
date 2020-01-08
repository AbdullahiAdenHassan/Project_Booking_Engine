package com.booking.project.bookingsystem.reservation.integration;

import com.booking.project.BookingEngineApp;
import com.booking.project.bookingsystem.reservation.ReservationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingEngineApp.class)
public class ReservationControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Sql({"src/test/resources/schema.sql","src/test/resources/data.sql"})
    @Test
    public void testRetrieveAllReservations() {
        String URI = "localhost:8080/api/reservations/allReservations";
        assertTrue(this.restTemplate.getForObject(URI, ReservationController.class)
        .getAllReservations().size() == 3);
    }
}
