package modelling;

import java.time.LocalDate;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Booking {

    private final Room room;
    private final Guest guest;
    private final LocalDate arrivalDate;
    private final LocalDate departureDate;
    private final Id hotelID;

    private Booking(Builder builder) {
        isTrue(builder.room.getRoomNumber()>0);
        notNull(builder.guest);
        isTrue(builder.guest.getName().matches("^[a-zA-Z]+"));

        this.room = builder.room;
        this.guest = builder.guest;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
        this.hotelID = builder.hotelID;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        //ToDO: Step-Builder-pattern, Link: https://svlada.com/step-builder-pattern/

        private Room room;
        private Guest guest;
        private LocalDate arrivalDate;
        private LocalDate departureDate;
        private Id hotelID;

        public Builder hotelID(final int hotelID){
            this.hotelID = Id.of(hotelID);
            return this;
        }

        public Builder withRoom(final int room) {
            this.room = Room.of(room);
            return this;
        }

        public Builder withGuest(final String guest) {
            this.guest = Guest.of(guest);
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
        return room.getRoomNumber();
    }

    public String getGuest() {
        return guest.getName();
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Id getHotelID() {
        return hotelID;
    }
}
