package com.booking.project.bookingsystem.reservations.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final UUID reservationId;
    private final Room room;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private final Guest guest;
    private final String hotel_brand;

    public Reservation(@JsonProperty("reservation_id") UUID id,
                       @JsonProperty("hotel_brand") String hotel_brand,
                       @JsonProperty("room") int room,
                       @JsonProperty("first_name") String first_name,
                       @JsonProperty("last_name") String last_name,
                       @JsonProperty("arrival_date") LocalDate arrivalDate,
                       @JsonProperty("departure_date") LocalDate departureDate) {
        this.reservationId = id;
        this.room = Room.of(room);
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.guest = Guest.of(first_name, last_name);
        this.hotel_brand = hotel_brand;
    }

    public UUID getReservationId() {
        return reservationId;
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

    public String getHotel_brand() {
        return hotel_brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(room, that.room) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(departureDate, that.departureDate) &&
                Objects.equals(guest, that.guest) &&
                Objects.equals(hotel_brand, that.hotel_brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, room, arrivalDate, departureDate, guest, hotel_brand);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", room=" + room +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", guestName=" + guest +
                ", hotelChain='" + hotel_brand + '\'' +
                '}';
    }

    public String getDate() {
        return getArrivalDate() + " and " + getDepartureDate();
    }
}
