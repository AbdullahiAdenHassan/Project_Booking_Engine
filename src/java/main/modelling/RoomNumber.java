package main.modelling;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

public class RoomNumber {
    private final int value;

    public RoomNumber(final int value) {
        isTrue(value > 0);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RoomNumber that = (RoomNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
