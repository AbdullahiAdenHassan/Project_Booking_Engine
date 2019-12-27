package com.booking.project.bookingsystem.reservations.dao;


import com.booking.project.bookingsystem.reservations.reservation.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationDao {

    public abstract int insertReservation(UUID id, Reservation reservation);
    public abstract List<Reservation> selectAllReservations();

    //List<Hotel> selectAllEmptyRooms(); //later!

   /* public void makeABooking(Booking booking);

    public List<Room> listAllRoomOfAHotel(int hotelID);

    public List<Booking> listAllBookedRooms();*/

}
