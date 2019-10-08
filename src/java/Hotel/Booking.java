package Hotel;

import java.util.HashMap;
import java.util.Scanner;


public class Booking {

    private Guest guest;
    private Rooms rooms;
    private HashMap <Rooms, String> booking = new HashMap<>();
    private Scanner in = new Scanner(System.in);
    private String m_input;

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

    public void listOfAllBookedRooms(){

    }

}