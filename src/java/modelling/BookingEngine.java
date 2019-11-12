package modelling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingEngine {
    private final Set<Integer> rooms;
    private List<Booking> reservations = new ArrayList<>();

    // 1. dep inject an inventory
    // 2. you should be able too book multiple hotels

    // Inventory class to keep the inventory of rooms
    // Dependency inject the inventory to the BookingEngine
    // i.e. 'public BookingEngine(final Inventory inventory)
    //
    // List<Hotel> inventory.hotels()
    // List<Room>  hotel.rooms()

    // Map<Hotel, List<Booking>>

    // Exempel:
    // hotel 2, rum 100, .....
    // inventory.getHotel(2).getRooms().includes(rum 100);
    // reservationsForHotel2 = reservations.getForHotel(2);
    // the usual check....

    public BookingEngine() {
        this.rooms = Set.of(
                101,
                102,
                103,
                104,
                105);
    }

    public Set<Integer> listAllRooms() {
        return rooms;
    }

    public List<Booking> listAllBookedRooms() {
        return reservations;
    }

    public void bookARoom(Booking booking) {
        boolean isThatRoomFreeASpecificDate = isThatRoomFreeASpecificDate(booking);
        boolean isThatRoomAcceptable = rooms.contains(booking.getRoom());

        if (!isThatRoomAcceptable)
            throw new IllegalArgumentException();

        if (!isThatRoomFreeASpecificDate)
            throw new RuntimeException();

        reservations.add(booking);
    }

    private boolean isThatRoomFreeASpecificDate(Booking booking) {
        if (reservations.isEmpty() || !getBookedRooms().contains(booking.getRoom()))
            return isThatDateAcceptable(booking);
        else
            return isThatDateAcceptable(booking) && isAvailable(booking);
    }

    private boolean isAvailable(Booking booking) {
        List<Booking> checkReservation = getSortedBookedDates(booking.getRoom());
        for (int i = 0; i < checkReservation.size(); i++) {

            if (booking.getArrivalDate().isEqual(checkReservation.get(i).getArrivalDate())
                    && booking.getDepartureDate().isEqual(checkReservation.get(i).getDepartureDate()))
                return false;

            if (booking.getArrivalDate().isAfter(checkReservation.get(i).getArrivalDate())
                    && booking.getDepartureDate().isBefore(checkReservation.get(i).getDepartureDate()))
                return false;

            if (booking.getArrivalDate().isBefore(checkReservation.get(i).getArrivalDate())
                    && (booking.getDepartureDate().isAfter(checkReservation.get(i).getDepartureDate())
                    || booking.getDepartureDate().isEqual(checkReservation.get(i).getDepartureDate())))
                return false;

            if (booking.getArrivalDate().isAfter(checkReservation.get(i).getArrivalDate())
                    && booking.getArrivalDate().isBefore(checkReservation.get(i).getDepartureDate()))
                return false;

            if (booking.getDepartureDate().isAfter(checkReservation.get(i).getArrivalDate())
                    && booking.getDepartureDate().isBefore(checkReservation.get(i).getDepartureDate()))
                return false;
        }
        return true;
    }

    private boolean isThatDateAcceptable(Booking booking) {
        if (booking.getArrivalDate().isEqual(LocalDate.now())
                || booking.getArrivalDate().isAfter(LocalDate.now()))
            return true;
        return false;
    }

    private List<Integer> getBookedRooms() {
        List<Integer> listOfBookedRooms = new ArrayList<>();
        reservations
                .stream()
                .forEach(reservation -> {
                    listOfBookedRooms.add(reservation.getRoom());
                });
        return listOfBookedRooms;
    }

    private List<Booking> getSortedBookedDates(int roomNumber) {
        return reservations
                .stream()
                .filter(b -> {
                    if (roomNumber != b.getRoom())
                        return false;
                    else
                        return true;
                })
                .sorted(Comparator.comparing(Booking::getArrivalDate))
                .collect(Collectors.toList());
    }
}
