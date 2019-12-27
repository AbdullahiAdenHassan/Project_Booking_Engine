package com.booking.project.Modelling;

import java.time.LocalDate;

public class Booking {

    private final Id hotelId;
    private final Room room;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private final Guest guest;

    public static hotelStep builder() {
        return new Builder();
    }

    public interface hotelStep {
        ToRoomStep withHotelId(int hotelId);
    }

    public interface ToRoomStep {
        ToArrivalDate withRoom(int room);
    }

    public interface ToArrivalDate {
        ToDepartureDate withArrivalDate(LocalDate arrivalDate);
    }

    public interface ToDepartureDate {
        ToGuestName withDepartureDate(LocalDate departureDate);
    }

    public interface ToGuestName {
        Build withGuest(String guest);
    }

    public interface Build {
        Booking build();
    }

    public static class Builder implements hotelStep, ToRoomStep, ToArrivalDate, ToDepartureDate, ToGuestName, Build {
        private Id hotelId;
        private Room room;
        private LocalDate arrivalDate;
        private LocalDate departureDate;
        private Guest guest;

        @Override
        public Booking build() {
            return new Booking(this);
        }

        @Override
        public ToRoomStep withHotelId(int hotelId) {
            this.hotelId = Id.of(hotelId);
            return this;
        }

        @Override
        public ToArrivalDate withRoom(int room) {
            this.room = Room.of(room);
            return this;
        }

        @Override
        public ToDepartureDate withArrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        @Override
        public ToGuestName withDepartureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        @Override
        public Build withGuest(String guest) {
            this.guest = Guest.of(guest);
            return this;
        }

    }

    private Booking(Builder builder) {
        this.hotelId = builder.hotelId;
        this.room = builder.room;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
        this.guest = builder.guest;
    }


    public Id getHotelId() {
        return hotelId;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Guest getGuest() {
        return guest;
    }

}
