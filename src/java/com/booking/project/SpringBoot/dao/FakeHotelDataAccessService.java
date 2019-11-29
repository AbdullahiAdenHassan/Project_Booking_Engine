package com.booking.project.SpringBoot.dao;

import com.booking.project.Modelling.Hotel;
import com.booking.project.Modelling.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fakeDao")
public class FakeHotelDataAccessService implements InventoryDao {
    private final List<Hotel> DB;

    //TODO: Replace Hotel form Modelling with Hotel from Dao.
    public FakeHotelDataAccessService() {
        this.DB = List.of(
                Hotel.builder().hotelID(1)
                        .withHotelName("Small")
                        .withListOfRoom(List.of(Room.of(101), Room.of(102)))
                        .build(),

                Hotel.builder().hotelID(2)
                        .withHotelName("Medium")
                        .withListOfRoom(List.of(Room.of(101), Room.of(102), Room.of(103)))
                        .build(),

                Hotel.builder().hotelID(3)
                        .withHotelName("Big")
                        .withListOfRoom(List.of(Room.of(101), Room.of(102), Room.of(103), Room.of(104), Room.of(105)))
                        .build()
        );
    }


    @Override
    public Hotel getHotel(int hotelNumber) {
        return null;
    }
}
