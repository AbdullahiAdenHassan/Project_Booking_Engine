package com.booking.project.Modelling;

import java.util.List;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Hotel {

    private final Id hotelID;
    private final Brand hotelName;
    private final List<Room> room;


    public Hotel(Builder builder) {
        this.hotelID = builder.hotelID;
        this.room = builder.room;
        this.hotelName = builder.hotelName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Id hotelID;
        private Brand hotelName;
        private List<Room> room;

        public Builder hotelID(final int hotelID) {
            isTrue(hotelID > 0);
            this.hotelID = Id.of(hotelID);
            return this;
        }

        public Builder withHotelName(final String hotelName) {
            notNull(hotelName);
            this.hotelName = Brand.of(hotelName);
            return this;
        }

        public Builder withListOfRoom(final List<Room> room) {
            notNull(room);
            this.room = room;
            return this;
        }

        public Hotel build() {
            return new Hotel(this);
        }

    }

    public Id getHotelID() {
        return hotelID;
    }

    public Brand getHotelName() {
        return hotelName;
    }

    public List<Room> getRoom() {
        return room;
    }
}
