package com.booking.project.bookingsystem.reservation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final UUID reservation_id;
    private final Room hotel_room;
    private final LocalDate arrival_date;
    private final LocalDate departure_date;
    private final Guest guest;
    private final HotelChain hotel_chain;

    public Reservation(@JsonProperty("reservation_id") UUID id,
                       @JsonProperty("hotel_chain") HotelChain hotel_chain,
                       @JsonProperty("hotel_room") int hotel_room,
                       @JsonProperty("first_name") String first_name,
                       @JsonProperty("last_name") String last_name,
                       @JsonProperty("arrival_date") LocalDate arrival_date,
                       @JsonProperty("departure_date") LocalDate departure_date) {
        this.reservation_id = id;
        this.hotel_room = Room.of(hotel_room);
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.guest = Guest.of(first_name, last_name);
        this.hotel_chain = hotel_chain;
    }

    public UUID getReservation_id() {
        return reservation_id;
    }

    public Room getHotel_room() {
        return hotel_room;
    }

    public LocalDate getArrival_date() {
        return arrival_date;
    }

    public LocalDate getDeparture_date() {
        return departure_date;
    }

    public Guest getGuest() {
        return guest;
    }

    public HotelChain getHotel_chain() {
        return hotel_chain;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservation_id, that.reservation_id) &&
                Objects.equals(hotel_room, that.hotel_room) &&
                Objects.equals(arrival_date, that.arrival_date) &&
                Objects.equals(departure_date, that.departure_date) &&
                Objects.equals(guest, that.guest) &&
                hotel_chain == that.hotel_chain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation_id, hotel_room, arrival_date, departure_date, guest, hotel_chain);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservation_id +
                ", room=" + hotel_room +
                ", arrivalDate=" + arrival_date +
                ", departureDate=" + departure_date +
                ", guest=" + guest +
                ", hotelChain=" + hotel_chain +
                '}';
    }

    public String getDate() {
        return getArrival_date() + " and " + getDeparture_date();
    }
}
