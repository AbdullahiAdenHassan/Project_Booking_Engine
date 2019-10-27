package main.modelling;

import java.time.LocalDate;
import java.util.*;

public class BookingEngine {
    private final Set<Room> rooms;
    private static Scanner in = new Scanner(System.in);
    private static String input;
    private static List<Booking> reservationListInfo = new ArrayList<>();
    private static List<String> reservationDate = new ArrayList<String>();
    private static List<Integer> reservationRoom = new ArrayList<>();

    public BookingEngine() {
        this.rooms = Set.of(
                new Room(new RoomNumber(101)),
                new Room(new RoomNumber(102)),
                new Room(new RoomNumber(103)),
                new Room(new RoomNumber(104)),
                new Room(new RoomNumber(105))
        );
        this.input = "4";
    }

    public void menu() {
        System.out.println("1) List all rooms");
        System.out.println("2) Book a room");
        System.out.println("3) List all booked rooms");
        System.out.println("4) Exit");
        System.out.print("Enter here: ");
        this.input = in.next();
    }

    public static void main(String[] args) {
        final var bookingEngine = new BookingEngine();
        bookingEngine.menu();
        while (!input.equals("4")) {
            if (input.equals("1")) {
                bookingEngine.listRooms();
                bookingEngine.menu();
            } else if (input.equals("2")) {
                bookingEngine.bookARoom();
                bookingEngine.menu();
            } else if (input.equals("3")) {
                bookingEngine.listAllBookedRooms();
                bookingEngine.menu();
            } else if (input.equals("4")) {
                input = "4";
            } else {
                System.out.println("Incorrect number,try again!");
                bookingEngine.menu();
            }
        }
    }

    public void bookARoom() {
        System.out.print("Entre a room number: ");
        int roomNumber = Integer.parseInt(in.next());

        System.out.print("Entre your name: ");
        String name = in.next();

        System.out.print("Entre your date: ");
        LocalDate date = LocalDate.parse(in.next());

        Booking booking = Booking.of(new Room(new RoomNumber(roomNumber)), date, Guest.of(name));
        if (isThatRoomFreeASpecificDate(booking) == true) {
            addBooking(booking);
            System.out.println("\nYou have successfully booked room nr "
                    + booking.getBookedRoom().getRoomNumber().getValue()
                    + "!");
        } else {
            System.out.println("That room is not free!");
        }
    }

    private boolean isThatRoomFreeASpecificDate(Booking booking) {
        if (booking.getDate().isEqual(LocalDate.now()) | booking.getDate().isAfter(LocalDate.now())) {
            if(!reservationDate.contains(booking.getDate().toString()))
            return true;

            if(!reservationRoom.contains(booking.getBookedRoom().getRoomNumber().getValue()))
                return true;
        }
        return false;
    }

    public List<Booking> listAllBookedRooms() {
        System.out.println("\nList of all booked rooms:");
        if (reservationListInfo.isEmpty()) {
            System.out.println("All rooms are free!");
        } else {
            reservationListInfo.forEach(System.out::println);
        }
        return null;
    }

    public void addBooking(Booking booking) {
        reservationListInfo.add(booking);
        reservationDate.add(booking.getDate().toString());
        reservationRoom.add(booking.getBookedRoom().getRoomNumber().getValue());
    }

    public Set<Room> listRooms() {
        System.out.println("\nList of all rooms:");
        rooms.forEach(room -> System.out.println("\t"+room));
        return null;
    }

}
