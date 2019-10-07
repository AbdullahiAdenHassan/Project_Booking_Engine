package Hotel;

import java.util.Scanner;

public class Booking {
    private static Scanner in = new Scanner(System.in);
    private static String input;

    public Booking()
    {

    }

    public void menu() {
        System.out.println("Choose one option");
        System.out.println("1) List of all rooms");
        System.out.println("2) Book a room");
        System.out.println("3) List all booked rooms");
        System.out.println("4) Exit");
        System.out.print("Enter here: ");
        input =  in.nextLine();
        while ( input != "4")
        {

            switch (input)
            {
                case "1": //List of all rooms

                    break;
                case "2": //Book a room

                    break;
                case "3": //List all booked rooms

                    break;

                default:
                    System.out.println("Nothing has been chosen, try again!");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            System.out.println("Clear");//????
            System.out.println("Choose one option");
            System.out.println("1) List of all rooms");
            System.out.println("2) Book a room");
            System.out.println("3) List all booked rooms");
            System.out.println("4) Exit");
            System.out.print("Enter here: ");
            input = in.nextLine();
        }
        in.close();
    }

}