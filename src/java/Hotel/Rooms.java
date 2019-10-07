package Hotel;

import java.util.HashMap;


public class Rooms {

    private HashMap<Integer,String> myRoom = new HashMap<Integer,String>();
    public Rooms()
    {

    }

    private void listAllRooms()
    {

    }

    private void listAllBookedRooms()
    {

    }

    private void bookARoom(int doorNumber,String nameOfPerson){
        if(isTheRoomFree(doorNumber,nameOfPerson)){
            myRoom.put(doorNumber,nameOfPerson);
        }
    }

    private boolean isTheRoomFree(int doorNumber, String nameOfPerson)
    {
        if(myRoom.put(doorNumber,nameOfPerson) == myRoom.put(doorNumber,null))
        return true;
        else
            return false;
    }
}
