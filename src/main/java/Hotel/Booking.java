package Hotel;

import java.io.IOException;
import java.util.Scanner;

public class Booking {
    private Scanner in = new Scanner(System.in);
    private Guest guest = new Guest();
    private Rooms rooms = new Rooms(guest);
    private String m_input;

    public Booking() {

    }

    public String menu() {
        System.out.println("1) List all rooms");
        System.out.println("2) Book a room");
        System.out.println("3) List all booked rooms");
        System.out.println("4) Exit");
        System.out.print("Enter here: ");
        m_input = in.next();
        return m_input;
    }

    public void bookARoom() {
        System.out.print("Entre a room number: ");
        m_input = in.next();
        rooms.setRoomNumber(m_input);

        System.out.print("Entre your name: ");
        m_input = in.next();
        guest.setName(m_input);

        System.out.print("Entre date: ");
        m_input = in.next();
        rooms.setDate(m_input);

        rooms.getARoomIfPossible();
    }

    public void clearConsole() {

        //Don't work
        /*try {
            Thread.sleep(3000);
            Runtime.getRuntime().exec("CLR");
        } catch (IOException | InterruptedException e) {
        }
        */
        // dirty solution.
        int newLine = 0;
        while (newLine != 15) {
            System.out.println();
            newLine++;
        }
    }

}