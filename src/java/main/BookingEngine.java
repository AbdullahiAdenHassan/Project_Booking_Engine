package main;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookingEngine {
    private final Set<Room> rooms;
    private List<Booking> reservations = new ArrayList<>();

    public BookingEngine() {
        this.rooms = Set.of(
                Room.of(101),
                Room.of(102),
                Room.of(103),
                Room.of(104),
                Room.of(105));
    }

    public Set<Room> listAllRooms() {
        return rooms;
    }

    public void bookARoom(Booking booking) {
        boolean isThatRoomFreeASpecificDate = isThatRoomFreeASpecificDate(booking);
        boolean isThatRoomAcceptable = rooms.contains(Room.of(booking.getBookedRoom().getRoomNumber()));

        if (!isThatRoomAcceptable)
            throw new IllegalArgumentException();

        if (!isThatRoomFreeASpecificDate)
            throw new RuntimeException();

        reservations.add(booking);
    }

    public List<Booking> listAllBookedRooms() {
        return reservations;
    }

    private boolean isThatRoomFreeASpecificDate(Booking booking) {
        if (reservations.isEmpty() || !getBookedRooms().contains(booking.getBookedRoom().getRoomNumber()))
            return isThatDateAcceptable(booking);
        else
            return isThatDateAcceptable(booking) && isAvailable(booking);
    }

    private boolean isAvailable(Booking booking) {
        List<Booking> checkReservation = getSortedBookedDates(booking.getBookedRoom().getRoomNumber());
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
                    listOfBookedRooms.add(reservation.getBookedRoom().getRoomNumber());
                });
        return listOfBookedRooms;
    }

    private List<Booking> getSortedBookedDates(int roomNumber) {
        return reservations
                .stream()
                .filter(b -> {
                    if (roomNumber != b.getBookedRoom().getRoomNumber())
                        return false;
                    else
                        return true;
                })
                .sorted(Comparator.comparing(Booking::getArrivalDate))
                .collect(Collectors.toList());
    }
}
//(b1,b2)->b1.getArrivalDate().compareTo(b2.getArrivalDate()) is the same as Comparator.comparing(Booking::getArrivalDate)
