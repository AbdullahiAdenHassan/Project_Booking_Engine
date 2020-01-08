package com.booking.project.bookingsystem.reservation.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

public class Room {
    private final int room;

    private Room(int room) {
        isTrue(room >= 0);
        this.room = room;
    }

    public static Room of (int room){
        return new Room(room);
    }

    public int getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room1 = (Room) o;
        return room == room1.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }

    @Override
    public String toString() {
        return "Room{" +
                "room=" + room +
                '}';
    }
}
