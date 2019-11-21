package modelling;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Inventory {

    private final List<Hotel> CollectionOfHotels;

    public Inventory() {
        this.CollectionOfHotels = List.of(
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

    public Hotel getHotel(int hotelNumber) {
        return CollectionOfHotels
                .stream()
                .filter(hotel -> hotelNumber == hotel.getHotelID().getId())
                .collect(Collectors.toList()).get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(CollectionOfHotels, inventory.CollectionOfHotels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CollectionOfHotels);
    }
}