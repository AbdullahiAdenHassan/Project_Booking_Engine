package Hotel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rooms {
    private static HashMap<String, String> m_reservation = new HashMap();
    private Guest m_guest;
    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");
    private List m_bookedRooms;
    private String m_room;
    private int m_id;

    public Rooms() {
        for (String rooms : m_roomslist)
            m_reservation.put(rooms, "");
    }

    public Rooms(Guest guest) {
        this();
        this.m_guest = guest;
    }

    public void listOfAllRooms() {
        System.out.println("List of all rooms: ");
        m_roomslist.forEach((k) -> System.out.println("\t" + k));
    }

    public void getARoomIfPossible() {
        if (isThatRoomFree(getRoomNumber()) == true)
            System.out.println("You have successfully booked room nr " + getRoomNumber() + "!");
        else
            System.out.println("That room is not free!");
    }

    public void listOfAllBookedRooms() {
        System.out.println("\nList of all booked rooms: ");
        m_bookedRooms = m_reservation
                .entrySet()
                .stream()
                .filter(m_reservation -> !m_reservation.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        m_bookedRooms.forEach(x -> System.out.println("Room " + x));
    }

    private boolean isThatRoomFree(String roomNumber) {
        if (Room(roomNumber)) {
            m_reservation.replace(roomNumber, "", m_guest.getName());
            return true;
        }
        return false;
    }

    private boolean Room(String roomNumber) {
        if (m_reservation.containsKey(roomNumber) & m_reservation.get(roomNumber).isEmpty())
            return true;
        return false;
    }

    public void setRoomNumber(String room) {
        this.m_room = room;
    }

    private String getRoomNumber() {
        return m_room;
    }
}
