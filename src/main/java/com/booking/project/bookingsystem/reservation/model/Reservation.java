package com.booking.project.bookingsystem.reservation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final UUID reservationId;
    private final Room hotelRoom;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private final Guest guest;
    private final HotelChain hotelChain;

    public Reservation(@JsonProperty("reservation_id") UUID id,
                       @JsonProperty("hotel_chain") HotelChain hotel_chain,
                       @JsonProperty("hotel_room") int hotelRoom,
                       @JsonProperty("first_name") String first_name,
                       @JsonProperty("last_name") String last_name,
                       @JsonProperty("arrival_date") LocalDate arrivalDate,
                       @JsonProperty("departure_date") LocalDate departureDate) {
        this.reservationId = id;
        this.hotelRoom = Room.of(hotelRoom);
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.guest = Guest.of(first_name, last_name);
        this.hotelChain = hotel_chain;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Room getHotelRoom() {
        return hotelRoom;
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

    public HotelChain getHotelChain() {
        return hotelChain;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId) &&
                Objects.equals(hotelRoom, that.hotelRoom) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(departureDate, that.departureDate) &&
                Objects.equals(guest, that.guest) &&
                hotelChain == that.hotelChain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, hotelRoom, arrivalDate, departureDate, guest, hotelChain);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", room=" + hotelRoom +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", guest=" + guest +
                ", hotelChain=" + hotelChain +
                '}';
    }

    public String getDate() {
        return getArrivalDate() + " and " + getDepartureDate();
    }
}
