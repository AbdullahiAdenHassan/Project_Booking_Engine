package main.modelling;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookingEngine {
    private final Set<Room> rooms;
    private List<Booking> reservations = new ArrayList<>();

    public BookingEngine() {
        this.rooms = Set.of(
                new Room(new RoomNumber(101)),
                new Room(new RoomNumber(102)),
                new Room(new RoomNumber(103)),
                new Room(new RoomNumber(104)),
                new Room(new RoomNumber(105))
        );
    }

    public Set<Room> listAllRooms() {
        return rooms;
    }

    public void bookARoom(Booking booking) throws Exception {

        if (isThatRoomFreeASpecificDate(booking)) {
            reservations.add(booking);
        } else {
            throw new Exception();
        }
    }

    public List<Booking> listAllBookedRooms() {
        return reservations;
    }

    private boolean isThatRoomFreeASpecificDate(Booking booking) {
        return isThatDateAcceptable(booking) && isThatRoomFree(booking);
    }


    private boolean isThatRoomFree(Booking booking) {
        AtomicBoolean isThatRoomFree = new AtomicBoolean(false);
        if (!getBookedRooms().contains(booking.getBookedRoom().getRoomNumber().getValue()))
            isThatRoomFree.set(true);
        else {
            reservations.stream().forEach(checkDate -> {
                if (booking.getArrivalDate().isBefore(checkDate.getArrivalDate())
                        && booking.getDepartureDate().isEqual(checkDate.getArrivalDate())
                        || booking.getDepartureDate().isBefore(checkDate.getArrivalDate()))
                    isThatRoomFree.set(true);

                if (booking.getArrivalDate().isEqual(checkDate.getDepartureDate())
                        || booking.getArrivalDate().isAfter(checkDate.getDepartureDate()))
                    isThatRoomFree.set(true);
            });
        }
        return isThatRoomFree.get();
    }

    private boolean isThatDateAcceptable(Booking booking) {
        if (booking.getArrivalDate().isEqual(LocalDate.now()) | booking.getArrivalDate().isAfter(LocalDate.now()))
            return true;
        return false;
    }

    private List<Integer> getBookedRooms() {
        List<Integer> listOfBookedRooms = new ArrayList<>();
        reservations.stream()
                .forEach(reservation -> {
                    listOfBookedRooms.add(reservation.getBookedRoom().getRoomNumber().getValue());
                });
        return listOfBookedRooms;
    }
}