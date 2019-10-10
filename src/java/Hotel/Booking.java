package Hotel;

import java.util.Scanner;

public class Booking {
    private static Scanner in = new Scanner(System.in);
    private static String m_input;
    private Guest guest = new Guest();
    private Rooms rooms = new Rooms(guest);

    public Booking()
    {

    }

    public String menu(){
        System.out.println("1) List all rooms");
        System.out.println("2) Book a room");
        System.out.println("3) List all booked rooms");
        System.out.println("4) Exit");
        System.out.print("Enter here: ");
        m_input = in.next();
        return m_input;
    }

    public void bookARoom(){
        System.out.print("Entre your name: ");
        m_input = in.next();
        guest.setName(m_input);

        System.out.print("Entre a room number: ");
        m_input = in.next();
        rooms.setRoomNumber(m_input);

        rooms.getARoomIfPossible();
    }

    public void viewAllBookedRooms()// maybe needlessly
    {
        rooms.listOfAllBookedRooms();
    }

}