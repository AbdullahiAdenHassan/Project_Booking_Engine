package Hotel;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Rooms {
    private static HashMap<String, String> m_reservation = new HashMap();
    private List<String> m_roomslist = Arrays.asList("101", "102", "103", "104", "105");
    private List<String> m_bookedRooms = new ArrayList<>();
    private List<LocalDate> m_dates = new ArrayList<>();
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

    private boolean isDateFormatterCorrect(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        try {
            m_dates.add(LocalDate.parse(date, format));
            return true;
        } catch (Exception e) {
            System.out.println("Failed to parse date");
            return false;
        }
    }

    private boolean isThatRoomFreeTest(String roomNumber, String date) {

        if (isDateFormatterCorrect(date)) {
            m_reservation.replace(roomNumber, "", m_guest.getName());
            m_dates.add(LocalDate.parse(date));
            System.out.println(m_dates.add(LocalDate.parse(date)));
            return true;
        } else if (m_dates.contains(date) & !m_bookedRooms.contains(roomNumber)) {
            m_reservation.replace(roomNumber, "", m_guest.getName());
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

    public void setDate(String date) {
        this.m_date = date;
    }
}
