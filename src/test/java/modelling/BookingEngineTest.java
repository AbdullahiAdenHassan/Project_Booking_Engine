package modelling;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingEngineTest {
    private BookingEngine bookingEngine = new BookingEngine(new Inventory());

    @Test
    void youShouldBeAbleToListAllRooms() {
        assertTrue((!bookingEngine.listAllRoomOfAHotel(1).isEmpty()));
        assertTrue(bookingEngine.listAllRoomOfAHotel(1).size() == 2);
    }

    @Test
    void youShouldBeAbleToBookARoom() {
        Booking booking = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Abbe")
                .build();

        bookingEngine.makeABooking(booking);

        assertEquals(1, bookingEngine.listAllBookedRooms().size());
    }

    @Test
    void youShouldNotBeAbleToBookANonExistingRoom() {
        Booking booking = Booking.builder()
                .hotelID(1)
                .withRoom(103)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(1))
                .withGuest("Abbe")
                .build();

        assertThrows(IllegalArgumentException.class, () -> bookingEngine.makeABooking(booking));
        assertTrue(bookingEngine.listAllBookedRooms().isEmpty());
    }


    @Test
    void youCanNotBookARoomInThePast() {
        Booking booking = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().minusDays(1))
                .withDepartureDate(LocalDate.now().plusDays(1))
                .withGuest("Abbe")
                .build();

        assertThrows(RuntimeException.class, () -> bookingEngine.makeABooking(booking));
        assertFalse(bookingEngine.listAllBookedRooms().contains(booking));
    }

    @Test
    void youCanNotBookARoomTwiceOnTheSameDay() {

        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(1))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(2)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(1))
                .withGuest("Anders")
                .build();


        Booking booking3 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(1))
                .withGuest("Edward")
                .build();


        bookingEngine.makeABooking(booking1);
        bookingEngine.makeABooking(booking2);
        assertThrows(RuntimeException.class, () -> bookingEngine.makeABooking(booking3));
        assertEquals(2, bookingEngine.listAllBookedRooms().size());

    }


    @Test
    void youShouldBeAbleTooBookARoomOverSeveralDays() {
        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().plusDays(2))
                .withDepartureDate(LocalDate.now().plusDays(4))
                .withGuest("Edward")
                .build();

        Booking booking3 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().plusDays(4))
                .withDepartureDate(LocalDate.now().plusDays(6))
                .withGuest("Anders")
                .build();

        bookingEngine.makeABooking(booking1);
        bookingEngine.makeABooking(booking2);
        bookingEngine.makeABooking(booking3);

        assertEquals(3, bookingEngine.listAllBookedRooms().size());
        assertEquals("Abbe", bookingEngine.listAllBookedRooms().get(0).getGuest());
        assertEquals("Edward", bookingEngine.listAllBookedRooms().get(1).getGuest());
        assertEquals("Anders", bookingEngine.listAllBookedRooms().get(2).getGuest());

    }

    @Test
    void youShouldNotBeAbleTooBookARoomInTheMiddleOfABookedDate() {

        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().plusDays(2))
                .withDepartureDate(LocalDate.now().plusDays(4))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(6))
                .withGuest("Anders")
                .build();

        bookingEngine.makeABooking(booking1);
        assertThrows(RuntimeException.class,()->bookingEngine.makeABooking(booking2));

    }

    @Test
    void youShouldNotBeAbleTooBookARoomBetweenBookedDates() {
        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(4))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().plusDays(1))
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Anders")
                .build();

        bookingEngine.makeABooking(booking1);
        assertThrows(RuntimeException.class,()->bookingEngine.makeABooking(booking2));
        assertEquals(1,bookingEngine.listAllBookedRooms().size());
        assertEquals("Abbe",bookingEngine.listAllBookedRooms().get(0).getGuest());
    }

    @Test
    void youShouldBeAbleTooBookMultipleRoomsOnTheSameDate() {
        Booking booking1 = Booking.builder()
                .hotelID(2)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(2)
                .withRoom(102)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Anders")
                .build();

        Booking booking3 = Booking.builder()
                .hotelID(2)
                .withRoom(103)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .withGuest("Edward")
                .build();

        bookingEngine.makeABooking(booking1);
        bookingEngine.makeABooking(booking2);
        bookingEngine.makeABooking(booking3);

    }

    @Test
    void youShouldNotBeAbleToBookBetweenABooking() {

        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now().plusDays(2))
                .withDepartureDate(LocalDate.now().plusDays(4))
                .withGuest("Abbe")
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(3))
                .withGuest("Anders")
                .build();

        bookingEngine.makeABooking(booking1);
        assertThrows(RuntimeException.class,()->bookingEngine.makeABooking(booking2));
        assertEquals(1,bookingEngine.listAllBookedRooms().size());
        assertEquals("Abbe",bookingEngine.listAllBookedRooms().get(0).getGuest());
    }


    @Test
    void youShouldOnlyHaveFiveRoomsInHotelBig() {
        assertEquals(5, bookingEngine.listAllRoomOfAHotel(3).size());
    }

    @Test
    void youShouldBookARoom() {
        Booking booking = Booking.builder()
                .hotelID(1)
                .withRoom(102)
                .withGuest("Abbe")
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .build();

        bookingEngine.makeABooking(booking);
        assertEquals(1, bookingEngine.listAllBookedRooms().size());
    }

    @Test
    void youShouldListAllRoomsOfAHotel() {
        Inventory inventory = new Inventory();
        assertEquals(2, inventory.getHotel(1).getRoom().size());
        assertEquals(3, inventory.getHotel(2).getRoom().size());
        assertEquals(5, inventory.getHotel(3).getRoom().size());
    }


    @Test
    void youShouldBeAbleToBookADifferentHotel() {
        Booking booking1 = Booking.builder()
                .hotelID(1)
                .withRoom(101)
                .withGuest("Abbe")
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .build();

        Booking booking2 = Booking.builder()
                .hotelID(2)
                .withRoom(101)
                .withGuest("Edward")
                .withArrivalDate(LocalDate.now())
                .withDepartureDate(LocalDate.now().plusDays(2))
                .build();

        bookingEngine.makeABooking(booking1);
        bookingEngine.makeABooking(booking2);

        assertEquals(2, bookingEngine.listAllBookedRooms().size());
    }

}
