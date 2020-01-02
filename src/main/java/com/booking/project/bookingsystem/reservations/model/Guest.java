package com.booking.project.bookingsystem.reservations.model;

import java.util.Objects;

public class Guest {
    private final String first_name;
    private final String last_name;

    private Guest(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public static Guest of(String first_name, String last_name) {
        return new Guest(first_name, last_name);
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(first_name, guest.first_name) &&
                Objects.equals(last_name, guest.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    /*public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(fullName, guest.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "fullName='" + fullName + '\'' +
                '}';
    }*/
}
