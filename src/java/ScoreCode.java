import Hotel.Booking;
import Hotel.Rooms;

public class ScoreCode {

    public static void main(String[] args) {
        Rooms rooms = new Rooms();
        Booking newBooking = new Booking();

        do {
            switch (newBooking.menu()) {
                case "1":
                    //list all rooms
                    rooms.listOfAllRooms();
                    break;
                case "2":
                    //Book a room

                    break;
                case "3":
                    //List all booked rooms
                    break;
                default:
                    //wrong number
                    break;
            }
        } while (!newBooking.menu().equals("4"));
    }
}
