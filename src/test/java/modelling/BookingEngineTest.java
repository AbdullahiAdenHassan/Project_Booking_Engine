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
                LocalDate.now().minusDays(1), LocalDate.now(),
                Guest.of("Abbe"));
        try {
            engine.bookARoom(booking);
            fail();
        } catch (Exception e) {
            assertTrue(!engine.listAllBookedRooms().contains(booking));
            System.out.println("You can not booking a room in the past, exception thrown!");
        }
    }

    @Test
    void youCanNotBookARoomTwiceOnTheSameDay() {
        Booking booking1 = Booking.of(
                new Room(new RoomNumber(101)), LocalDate.now(),
                LocalDate.now().plusDays(1),
                Guest.of("Abbe"));

        Booking booking2 = Booking.of(
                new Room(new RoomNumber(101)), LocalDate.now(),
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
            System.out.println("You can not booking the same room twice on the same day!");
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
            assertEquals(engine.listAllBookedRooms().size(), 3);

            assertEquals(engine.listAllBookedRooms().get(0).getArrivalDate(), LocalDate.now());
            assertEquals(engine.listAllBookedRooms().get(0).getDepartureDate(), LocalDate.now().plusDays(2));
            assertEquals(engine.listAllBookedRooms().get(0).getBookedRoom().getRoomNumber().getValue(), 101);

            assertEquals(engine.listAllBookedRooms().get(1).getGuest().getName(), "Anders");
            assertEquals(engine.listAllBookedRooms().get(1).getBookedRoom().getRoomNumber().getValue(), 101);

            assertEquals(engine.listAllBookedRooms().get(2).getGuest().getName(), "Edward");
            assertEquals(engine.listAllBookedRooms().get(2).getBookedRoom().getRoomNumber().getValue(), 101);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void youShouldNotBeAbleTooBookARoomInTheMiddleOfABookedDate() {
        Booking booking1 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), Guest.of("Abbe"));
        Booking booking2 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now(), LocalDate.now().plusDays(6), Guest.of("Anders"));
        try {
            engine.bookARoom(booking1);
            engine.bookARoom(booking2);
            fail();
        } catch (Exception e) {
            System.out.println("Trying too book a room in the middle of a booked date,exception thrown!");
            assertEquals(engine.listAllBookedRooms().size(),1);
        }
    }

    @Test
    void youShouldNotBeAbleTooBookARoomBetweenBookedDates() {
        Booking booking1 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now(), LocalDate.now().plusDays(4), Guest.of("Abbe"));
        Booking booking2 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), Guest.of("Anders"));
        try {
            engine.bookARoom(booking1);
            engine.bookARoom(booking2);
            fail("illegal booking!");
        } catch (Exception e) {
            System.out.println("Trying to book a room between a booked date, exception thrown!");
           assertEquals(engine.listAllBookedRooms().size(),1);
           assertEquals(engine.listAllBookedRooms().get(0).getGuest().getName(),"Abbe");
        }
    }

    @Test
    void youShouldBeAbleTooBookMultipleRoomsOnTheSameDate() {
        Booking booking1 = Booking.of(new Room(new RoomNumber(101)), LocalDate.now(), LocalDate.now().plusDays(2), Guest.of("Abbe"));
        Booking booking2 = Booking.of(new Room(new RoomNumber(102)), LocalDate.now(), LocalDate.now().plusDays(2), Guest.of("Anders"));
        Booking booking3 = Booking.of(new Room(new RoomNumber(103)), LocalDate.now(), LocalDate.now().plusDays(2), Guest.of("Edward"));

        try {
            engine.bookARoom(booking1);
            engine.bookARoom(booking2);
            engine.bookARoom(booking3);
            System.out.println("Multiple rum has been booked on the same date!");
        } catch (Exception e) {
            fail();
        }
    }
}

/*
@Test
    void Example() {
        Booking booking1 = Booking.of(
                new Room(new RoomNumber(105)), LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(1),
                Guest.of("Abbe"));
        Booking booking2 = Booking.of(
                new Room(new RoomNumber(102)), LocalDate.now(),
                LocalDate.now().plusDays(1),
                Guest.of("Edward"));

        Stream.of(booking1, booking2)
                .sorted((b1, b2) -> b1.getBookedRoom().getRoomNumber().getValue() - b2.getBookedRoom().getRoomNumber().getValue())
                .map(booking -> booking.getGuest().getName())
                .forEach(name -> System.out.println(name));
    }
 */