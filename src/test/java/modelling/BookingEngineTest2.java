package modelling;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingEngineTest2 {

    @Test
    void youShouldOnlyHaveFiveRooms() {
       BookingEngine bookingEngine = new BookingEngine();
       assertEquals(5,bookingEngine.listAllRooms().size());
    }

    @Test
    void youShouldBookARoom() {
        BookingEngine bookingEngine =  new BookingEngine();
        Booking booking = Booking.builder()
                .room(101)
                .withGuest("Abbe")
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .build();
        bookingEngine.bookARoom(booking);

        assertEquals(1,bookingEngine.listAllBookedRooms().size());
    }
}