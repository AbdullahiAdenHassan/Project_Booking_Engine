package com.booking.project.SpringBoot.dao;

import com.booking.project.Modelling.Hotel;


public interface InventoryDao {
    Hotel getHotel(int hotelNumber);
}
