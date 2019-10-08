package Hotel;

import java.util.*;

public class Rooms {

    private static Scanner in = new Scanner(System.in);
    private HashMap<String, String> myRoom = new HashMap<>();
    private String doorNumber;
    private String nameOfPerson;

    public Rooms()
    {
        myRoom.put("101","");
        myRoom.put("102","");
        myRoom.put("103","");
        myRoom.put("104","");
        myRoom.put("105","");
    }

    public void listAllRooms()
    {
        Set set = myRoom.entrySet();
        Iterator integer = set.iterator();
        while (integer.hasNext()){
            Map.Entry rooms = (Map.Entry) integer.next();
            System.out.println("Room "+rooms.getKey()+rooms.getValue());
        }
    }

    public void listAllBookedRooms()
    {

    }

    public void bookARoom(){

        System.out.print("Submit door number: ");
        doorNumber = in.next();
        System.out.print("Submit name: ");
        nameOfPerson = in.next();
        if(isTheRoomFree(doorNumber,nameOfPerson)){
            myRoom.put(doorNumber,nameOfPerson);
        }
    }

    private boolean isTheRoomFree(String doorNumber, String nameOfPerson)
    {
        /*
        if(myRoom.put(doorNumber,nameOfPerson) == myRoom.put(doorNumber,null))
        return true;
        else
            return false;

*/
        return false;
    }

}
