package Hotel;

import java.util.*;

public class Rooms {
    private Guest m_guest;
    private String m_room = null;

    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");
    private int hotelSize = m_roomslist.size();

    private ListIterator<String> m_rooms = m_roomslist.listIterator();
    private HashMap<String, String> reservation = new HashMap();
    private String m_reservation[][] = new String[hotelSize][2];

    public Rooms() {

    }

    public Rooms(Guest guest) {
        this.m_guest = guest;
    }

    public void listOfAllRooms() {
        while (m_rooms.hasNext()) {
            setRoomNumber(m_rooms.next());
            System.out.println("Room " + getRoomNumber());
        }
    }

    public void getARoomIfPossible()
    {
        if (isThatRoomFree(getRoomNumber()) == true)
            System.out.println("Your room is booked!");
        else
            System.out.println("That room is not free!");
    }
    public void listOfAllBookedRooms(){

    }

    private boolean isThatRoomFree(String roomNumber)
    {
        if (reservation.isEmpty()) {
            while (m_rooms.hasNext()) {
                String roomsKey = m_rooms.next();
                reservation.put(roomsKey, "");
            }
        }

        if (reservation.containsKey(roomNumber) & reservation.get(roomNumber).isEmpty() ) {
            reservation.replace(roomNumber,"",m_guest.getName());
            return true;
        }
        return false;
    }

    public void setRoomNumber(String room) {
        this.m_room = room;
    }

    private String getRoomNumber() {
        return m_room;
    }
}
