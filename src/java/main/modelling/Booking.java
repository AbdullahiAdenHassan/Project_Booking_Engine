package main.modelling;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private final Room room;
    private final LocalDate date;
    private final Guest guest;

    private Booking(Room room, LocalDate date, Guest guest) {
        this.room = room;
        this.date = date;
        this.guest = guest;
    }

    public static Booking of(Room room, LocalDate date, Guest guest) {
        return new Booking(room,date,guest);
    }

    public LocalDate getDate() {
        return date;
    }

    public Room getBookedRoom(){
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(room, booking.room) &&
                Objects.equals(date, booking.date) &&
                Objects.equals(guest, booking.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, date, guest);
    }

    @Override
    public String toString() {
        return "Room: "+room +
                ", Date: " + date +
                ", Guest: " + guest;
    }
}
