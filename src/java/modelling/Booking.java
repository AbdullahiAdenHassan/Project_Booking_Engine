package modelling;

import java.time.LocalDate;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Booking {

    private final int room;
    private final String guest;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;

    private Booking(Builder builder) {
        isTrue(builder.room>0);
        notNull(builder.guest);
        isTrue(builder.guest.matches("^[a-zA-Z]+"));

        this.room = builder.room;
        this.guest = builder.guest;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private int room;
        private String guest;
        private LocalDate arrivalDate;
        private LocalDate departureDate;

        public Builder room(final int room) {
            this.room = room;
            return this;
        }

        public Builder withGuest(final String guest) {
            this.guest = guest;
            return this;
        }

        public Builder withArrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder withDepartureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Booking build(){
            return new Booking(this);
        }

    }

    public int getRoom() {
        return room;
    }

    public String getGuest() {
        return guest;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}
