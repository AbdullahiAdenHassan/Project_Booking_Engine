import Hotel.Booking;
import Hotel.Rooms;

public class ScoreCode {

    public static void main(String[] args) {
        Rooms rooms = new Rooms();
        Booking newBooking = new Booking();
        String Input = newBooking.menu();
        while (!Input.equals("4")) {
            switch (Input) {
                case "1":
                    //list all rooms
                    rooms.listOfAllRooms();
                    newBooking.clearConsole();
                    break;
                case "2":
                    //Book a room
                    newBooking.bookARoom();
                    newBooking.clearConsole();
                    break;
                case "3":
                    //List all booked rooms
                    rooms.listOfAllBookedRooms();
                    newBooking.clearConsole();
                    break;
                default:
                    //wrong number
                    System.exit(0);
            }
            Input = newBooking.menu();
        }
    }
}
