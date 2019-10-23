package modelling;

import java.util.Set;

public class BookingEngine {
    private final Set<Room> rooms;

    BookingEngine() {
        this.rooms = Set.of(
                new Room(new RoomNumber(101)),
                new Room(new RoomNumber(102)),
                new Room(new RoomNumber(103)),
                new Room(new RoomNumber(104)),
                new Room(new RoomNumber(105))
        );
    }

    private void printRooms() {
        rooms.forEach(System.out::println);
    }

    public static void main(String[] args) {
        final var bookingEngine = new BookingEngine();

        bookingEngine.printRooms();
    }
}
