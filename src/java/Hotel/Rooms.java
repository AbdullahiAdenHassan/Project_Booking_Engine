package Hotel;

import java.util.*;

public class Rooms {
    private Guest m_guest;
    private String m_room = null;
    private int m_id = 1;
    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");

    private ListIterator<String> m_rooms = m_roomslist.listIterator();
    private HashMap<String, String> reservation = new HashMap();
    private String roomsKey;

    public Rooms() {
        while (m_rooms.hasNext()) {
            m_room = m_rooms.next();
            reservation.put(m_room, "");
        }
    }

    public Rooms(Guest guest) {
        this();
        this.m_guest = guest;
    }

    public void listOfAllRooms() {
        List<String> allRooms = new ArrayList<>(reservation.keySet());
        allRooms.forEach(System.out::println);
    }

    public void getARoomIfPossible() {
        if (isThatRoomFree(getRoomNumber()) == true)
            System.out.println("You have successfully booked room nr "+getRoomNumber()+"!");
        else
            System.out.println("That room is not free!");
    }

    public void listOfAllBookedRooms() {
        System.out.println("ID "+" Room"+"  Name");
        Iterator it = reservation.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry paris = (Map.Entry) it.next();
            if(!paris.getValue().toString().isEmpty()){
            System.out.println(m_id++ +"   "+paris.getKey()+ "   "+paris.getValue());
            }
        }
    }

    private boolean isThatRoomFree(String roomNumber) {

        Boolean isARoomFree = reservation.containsKey(roomNumber) & reservation.get(roomNumber).isEmpty();
        if (isARoomFree) {
            reservation.replace(roomNumber, "", m_guest.getName());
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
