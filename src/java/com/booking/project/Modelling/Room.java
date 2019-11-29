package com.booking.project.Modelling;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Room {
    private int roomNumber;

    private Room(final int roomNumber) {
        notNull(roomNumber);
        isTrue(roomNumber > 0);
        this.roomNumber = roomNumber;
    }

    public static Room of(final int roomNumber) {
        return new Room(roomNumber);
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String toString() {
        return "" + roomNumber;
    }
}
