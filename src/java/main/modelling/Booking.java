package main.modelling;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private final Room room;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private final Guest guest;

    private Booking(Room room, LocalDate arrivalDate, LocalDate departureDate, Guest guest) {
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.guest = guest;
    }

    public static Booking of(Room room, LocalDate arrivalDate,LocalDate departureDate, Guest guest) {
        return new Booking(room,arrivalDate,departureDate,guest);
    }


    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
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
                Objects.equals(arrivalDate, booking.arrivalDate) &&
                Objects.equals(guest, booking.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, arrivalDate, guest);
    }

    @Override
    public String toString() {
        return "Room: "+room +
                ", Date: " + arrivalDate +
                ", Guest: " + guest;
    }

}
