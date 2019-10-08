import Hotel.Booking;
import Hotel.Rooms;

public class ScoreCode {

    public static void main(String[] args) {
        Rooms rooms = new Rooms();
        Booking newBooking = new Booking();
        String Input = newBooking.menu();
        while(!Input.equals("4"))
        {
            switch (Input) {
                case "1":
                    //list all rooms
                    rooms.listOfAllRooms();
                    Input = newBooking.menu();
                    break;
                case "2":
                    //Book a room

                    Input = newBooking.menu();
                    break;
                case "3":
                    //List all booked rooms

                    Input = newBooking.menu();
                    break;
                default:
                    //wrong number

                    Input = newBooking.menu();
                    break;
            }
        }
    }
}
