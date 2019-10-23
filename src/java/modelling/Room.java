package modelling;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Room {
    private final RoomNumber roomNumber;

    public Room(final RoomNumber roomNumber) {
        this.roomNumber = notNull(roomNumber);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public String toString() {
        return "Room number " + roomNumber.getValue();
    }
}
