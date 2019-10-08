package Hotel;

import java.util.*;

public class Rooms {
    private String m_room;
    private List <String> m_roomslist = Arrays.asList("101", "102","103","104","105");
    private ListIterator<String> m_rooms = m_roomslist.listIterator();
    public Rooms()
    {

    }

    public void listOfAllRooms() {
        while (m_rooms.hasNext()){
            System.out.println("Room "+m_rooms.next());
        }
    }

    //maybe later useful.
    private void setRoom(String room){
        this.m_room = room;
    }
    private String getRoom(){
        return m_room;
    }

}
