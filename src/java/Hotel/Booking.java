package Hotel;

import java.util.Scanner;

public class Booking {
    private static Scanner in = new Scanner(System.in);
    private static String input;

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