package Hotel;

import java.util.*;

public class Rooms {
    private static HashMap<String, String> m_reservation = new HashMap();
    private Guest m_guest;
    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");
    private ListIterator<String> m_rooms = m_roomslist.listIterator();
    private Iterator m_hotelRooms;
    private Iterator m_bookedRooms;
    private String m_room;
    private int m_id;

    public Rooms() {
        while (m_rooms.hasNext()) {
            m_room = m_rooms.next();
            m_reservation.put(m_room, "");
        }
    }

    public Rooms(Guest guest) {
        this();
        this.m_guest = guest;
    }

    public void listOfAllRooms() {
        m_hotelRooms = m_reservation.entrySet().iterator();
        System.out.println("Rooms:");
        while (m_hotelRooms.hasNext()) {
            Map.Entry roomsNumber = (Map.Entry) m_hotelRooms.next();
            System.out.println("\t" + roomsNumber.getKey().toString());
        }
    }

    public void getARoomIfPossible() {
        if (isThatRoomFree(getRoomNumber()) == true)
            System.out.println("You have successfully booked room nr " + getRoomNumber() + "!");
        else
            System.out.println("That room is not free!");
    }

    public void listOfAllBookedRooms() {
        System.out.println("ID " + " Room" + "  Name");
        m_bookedRooms = m_reservation.entrySet().iterator();
        m_id = 1;
        while (m_bookedRooms.hasNext()) {
            Map.Entry paris = (Map.Entry) m_bookedRooms.next();
            if (paris.getValue().toString().matches("[a-zA-Z0-9]+")) {
                System.out.println(m_id++ + "   " + paris.getKey() + "   " + paris.getValue());
            }
        }
    }

    private boolean isThatRoomFree(String roomNumber) {
        if (isRoomFree(roomNumber)) {
            m_reservation.replace(roomNumber, "", m_guest.getName());
            return true;
        }
        return false;
    }

    private boolean isRoomFree(String roomNumber) {
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
