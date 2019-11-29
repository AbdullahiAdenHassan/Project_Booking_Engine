package com.booking.project.Modelling;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookingEngine {

    private final Inventory inventory;
    private List<Booking> reservations = new ArrayList<>();

    public BookingEngine(final Inventory inventory) {
        this.inventory = inventory;
    }

    public List<Room> listAllRoomOfAHotel(int hotelID) {
        return inventory.getHotel(hotelID).getRoom();
    }

    public void makeABooking(Booking booking) {

        if (!isHotelIdAndRoomNumberAcceptable(booking))
            throw new IllegalArgumentException();


        if (!isThatDateAcceptable(booking))
            throw new RuntimeException();


        if (!isThatRoomFreeASpecificDate(booking))
            throw new RuntimeException();

        reservations.add(booking);
    }

    private boolean isThatRoomFreeASpecificDate(Booking booking) {
        if (reservations.isEmpty()) {
            return true;
        } else
            return isBookedDateAvailable(booking);
    }

    private List<Booking> getSortedBookedDates(Booking booking) {
        return reservations.stream()
                .filter(hotelId -> hotelId.getHotelId().getId() == booking.getHotelId().getId())
                .filter(roomNumber -> booking.getRoom().getRoomNumber() == roomNumber.getRoom().getRoomNumber())
                .sorted(Comparator.comparing(Booking::getArrivalDate))
                .collect(Collectors.toList());
    }

    private boolean isBookedDateAvailable(Booking booking) {
        List<Booking> checkReservation = getSortedBookedDates(booking);
        for (Booking value : checkReservation) {

            if (booking.getArrivalDate().isEqual(value.getArrivalDate())
                    && booking.getDepartureDate().isEqual(value.getDepartureDate()))
                return false;

            if (booking.getArrivalDate().isAfter(value.getArrivalDate())
                    && booking.getDepartureDate().isBefore(value.getDepartureDate()))
                return false;

            if (booking.getArrivalDate().isBefore(value.getArrivalDate())
                    && (booking.getDepartureDate().isAfter(value.getDepartureDate())
                    || booking.getDepartureDate().isEqual(value.getDepartureDate())))
                return false;

            if (booking.getArrivalDate().isAfter(value.getArrivalDate())
                    && booking.getArrivalDate().isBefore(value.getDepartureDate()))
                return false;

            if (booking.getDepartureDate().isAfter(value.getArrivalDate())
                    && booking.getDepartureDate().isBefore(value.getDepartureDate()))
                return false;
        }
        return true;
    }


    private boolean isHotelIdAndRoomNumberAcceptable(Booking booking) {
        return inventory
                .getHotel(booking.getHotelId().getId())
                .getRoom()
                .stream()
                .anyMatch(room -> room.getRoomNumber() == booking.getRoom().getRoomNumber());
    }

    private boolean isThatDateAcceptable(Booking booking) {
        return booking.getArrivalDate().isEqual(LocalDate.now())
                || booking.getArrivalDate().isAfter(LocalDate.now());
    }

    public List<Booking> listAllBookedRooms() {
        return reservations;
    }
}
