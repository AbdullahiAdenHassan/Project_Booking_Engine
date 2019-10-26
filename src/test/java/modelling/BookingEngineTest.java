package modelling;


import main.modelling.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingEngineTest {

    private BookingEngine engine = new BookingEngine();

    @Test
    @Disabled
    void listRooms() {
        var rooms = engine.listRooms();

        assertTrue(!rooms.isEmpty());
        assertTrue(rooms.size() == 5);
    }

    @Test
    @Disabled
    void bookARoom() {
        Booking booking =  Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now().plusDays(1),
                Guest.of("Abbe"));

        engine.addBooking(booking);

        var bookings = engine.listAllBookedRooms();
        assertEquals(1, bookings.size());
        assertEquals(bookings.get(0), booking);
    }

    @Test
    @Disabled
    void youCanNotBookARoomTwiceOnTheSameDay() {
        Booking booking1 =  Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now().plusDays(1),
                Guest.of("Abbe"));

        Booking booking2 =  Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now().plusDays(1),
                Guest.of("Edward"));

        try {
            engine.addBooking(booking1);
            engine.addBooking(booking2);
        } catch (Exception e) {
            var bookings = engine.listAllBookedRooms();
            assertEquals(1, bookings.size());
            assertEquals(bookings.get(0), booking1);
        }
        fail();
    }

    @Test
    @Disabled
    void youCanNotBookARoomInThePast() {
        Booking booking =  Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now().minusDays(1),
                Guest.of("Abbe"));

        try {
            engine.addBooking(booking);
        } catch (Exception e) {
            assertTrue(engine.listAllBookedRooms().isEmpty());
        }
        fail();
    }
}