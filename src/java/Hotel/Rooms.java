package Hotel;

import javax.management.ObjectName;
import java.lang.invoke.VarHandle;
import java.util.*;

public class Rooms {
    private Guest m_guest;
    private String m_room;

    private List <String> m_roomslist = Arrays.asList("101", "102","103","104","105");
    private ListIterator<String> m_rooms = m_roomslist.listIterator();
    private HashMap<String,ListIterator> reservation = new HashMap();

    public Rooms()
    {

    }

    public Rooms(Guest guest)
    {
        this.m_guest = guest;
    }

    public void listOfAllRooms() {
        while (m_rooms.hasNext()){
            System.out.println("Room "+m_rooms.next());
        }
    }

    public void getARoomIfPossible() // does not work correct!
    {
        if(isARoomFree(getRoomNumber()) == true) {
            reservation.put(m_guest.getName(), m_roomslist.listIterator());
            System.out.println(getRoomNumber()+" "+m_guest.getName());
        }
        else
            System.out.println("That room is not free!");

    }
    private boolean isARoomFree(String room) //incorrect logic.
    {
        while (m_rooms.hasNext()){
           if(m_rooms.next().equals(room) & !reservation.containsKey(m_guest.getName()))
               return true;
        }
        return false;
    }


    //maybe later useful.
    public void setRoomNumber(String room){
        this.m_room = room;
    }
    private String getRoomNumber(){
        return m_room;
    }

}
