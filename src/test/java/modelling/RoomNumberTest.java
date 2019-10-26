package modelling;

import main.modelling.RoomNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RoomNumberTest {

    @Test
    void shouldWork() {
        final var roomNumber = new RoomNumber(123);
    }

    @Test
    void shouldThrowExceptionIfNegativeRoomNumber() {
        try {
            new RoomNumber(-123);
            fail();
        } catch (Exception e) {
        }
    }
}