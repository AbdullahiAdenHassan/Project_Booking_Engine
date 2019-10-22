package Hotel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Rooms {
    private static HashMap<String, String> m_reservation = new HashMap();
    private static List<String> m_bookedRooms = new ArrayList<>();
    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");
    private List<String> m_dates = new ArrayList<>();
    private Guest m_guest;
    private String m_room;
    private String m_date;

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
        if (isThatRoomFreeTest(getRoomNumber(), m_date) == true)
            System.out.println("You have successfully booked room nr " + getRoomNumber() + "!");
    }

    public void listOfAllBookedRooms() {
        System.out.println("\nList of all booked rooms: ");
        m_bookedRooms.forEach(x -> System.out.println("Room " + x));
    }

    private void updateBookedRooms(){
        m_bookedRooms = m_reservation
                .entrySet()
                .stream()
                .filter(m_reservation -> !m_reservation.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    private boolean isThatRoomFreeTest(String roomNumber, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            date = formatter.format(localDate);
            if (!m_dates.contains(date)) {
                m_reservation.replace(roomNumber, "", m_guest.getName());
                updateBookedRooms();
                m_dates.add(date);
                return true;
            } else if (!m_bookedRooms.contains(roomNumber)) {
                m_reservation.replace(roomNumber, "", m_guest.getName());
                updateBookedRooms();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Failed to parse date");
            return false;
        }
        System.out.println("That room is not free!");
        return false;
    }

    public void setRoomNumber(String room) {
        this.m_room = room;
    }

    private String getRoomNumber() {
        return m_room;
    }

    public void setDate(String date) {
        this.m_date = date;
    }
}
