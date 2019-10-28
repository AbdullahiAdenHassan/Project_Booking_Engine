package modelling;


import main.modelling.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BookingEngineTest {
    private BookingEngine engine = new BookingEngine();

    @Test
    void listAllRooms() {
        assertTrue((!engine.listAllRooms().isEmpty()));
        assertTrue(engine.listAllRooms().size() == 5);
        System.out.println("Successful, listing all rooms!");
    }

    @Test
    void bookARoom() {
        Booking booking = Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now(), LocalDate.now().plusDays(1),
                Guest.of("Abbe"));
        try {
            engine.bookARoom(booking);
            assertEquals(1, engine.listAllBookedRooms().size());
            assertEquals(engine.listAllBookedRooms().get(0), booking);
            System.out.println("Successful, booking a room!");
        } catch (Exception e) {
            fail("Failure");
        }
    }

    @Test
    void youCanNotBookARoomInThePast() {
        Booking booking = Booking.of(
                new Room(new RoomNumber(101)),
                LocalDate.now().minusDays(1),LocalDate.now(),
                Guest.of("Abbe"));
        try {
            engine.bookARoom(booking);
            fail();
        } catch (Exception e) {
            assertTrue(!engine.listAllBookedRooms().contains(booking));
            System.out.println("Successful, you can not booking a room in the past!");
        }
    }

    @Test
    void youCanNotBookARoomTwiceOnTheSameDay() {
        Booking booking1 = Booking.of(
                new Room(new RoomNumber(101)),LocalDate.now(),
                LocalDate.now().plusDays(1),
                Guest.of("Abbe"));

        Booking booking2 = Booking.of(
                new Room(new RoomNumber(101)),LocalDate.now(),
                LocalDate.now().plusDays(1),
                Guest.of("Edward"));

        try {
            engine.bookARoom(booking1);
            engine.bookARoom(booking2);
            System.out.println("Same room twice on the same day, bad booking!");
            fail();
        } catch (Exception e) {
            var bookings = engine.listAllBookedRooms();
            assertEquals(1, bookings.size());
            assertEquals(bookings.get(0), booking1);
            System.out.println("Successful, you can not booking the same room twice on the same day!");
        }
    }

    @Test
    void youShouldBeAbleTooBookARoomOverSereralDays() {
        Booking booking1 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now(), LocalDate.now().plusDays(2), Guest.of("Abbe"));
        Booking booking2 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), Guest.of("Anders"));
        Booking booking3 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now().plusDays(4), LocalDate.now().plusDays(6), Guest.of("Edward"));
        try {
            engine.bookARoom(booking1);
            engine.bookARoom(booking2);
            engine.bookARoom(booking3);
            assertEquals (engine.listAllBookedRooms().size(), 3);

            assertEquals(engine.listAllBookedRooms().get(0).getArrivalDate(),LocalDate.now());
            assertEquals(engine.listAllBookedRooms().get(0).getDepartureDate(),LocalDate.now().plusDays(2));
            assertEquals(engine.listAllBookedRooms().get(0).getBookedRoom().getRoomNumber().getValue(),101);

            assertEquals(engine.listAllBookedRooms().get(1).getGuest().getName(),"Anders");
            assertEquals(engine.listAllBookedRooms().get(1).getBookedRoom().getRoomNumber().getValue(),101);

            assertEquals(engine.listAllBookedRooms().get(2).getGuest().getName(),"Edward");
            assertEquals(engine.listAllBookedRooms().get(2).getBookedRoom().getRoomNumber().getValue(),101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//   @Test
// void youShouldBeAbleTooBookARoomOverSeveralDays() {
// booking1 new Booking(Room, 5, 8, Guest)
// booking2 new Booking(Room, 7, 9, Guest)
// bookingEngine.addBooking(booking1)
// bookingEngine.addBooking(booking2)
// make sure addBooking2 throws an exception
//    }
