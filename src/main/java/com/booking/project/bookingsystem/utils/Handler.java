package com.booking.project.bookingsystem.utils;

import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class Handler {


    public static JSONArray jacksonReadingJsonFiles(String path) {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray reservationList = new JSONArray();
        try {
            reservationList = mapper.readValue(new File(path), JSONArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public static Reservation convertDataToReservationObject(Object reservation) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(reservation);
        JsonNode jsonNode_hotel_room = jsonNode.get("hotel_room");
        JsonNode jsonNode_guests = jsonNode.get("guest");

        UUID reservation_id = UUID.fromString(jsonNode.get("reservation_id").asText());

        HotelChain hotel_chain = HotelChain.valueOf(jsonNode.get("hotel_chain").asText());

        //int hotel_room = Integer.parseInt(Optional.of(jsonNode.get("hotel_room")).orElse(jsonNode_hotel_room.get("room")).asText());
        int hotel_room = 0;
        if (jsonNode.get("hotel_room").isValueNode()) {
            hotel_room = Integer.parseInt(jsonNode.get("hotel_room").asText());
        } else {
            hotel_room = Integer.parseInt(jsonNode_hotel_room.get("room").asText());
        }

        //String first_name = Optional.ofNullable(jsonNode.get("first_name")).orElse(jsonNode_guests.get("first_name")).textValue();
        String first_name;
        if (jsonNode.get("first_name") != null) {
            first_name = jsonNode.get("first_name").toString();
        } else {
            first_name = jsonNode_guests.get("first_name").toString();
        }

        //String last_name = Optional.of(jsonNode.get("last_name")).orElse(jsonNode_guests.get("last_name")).asText();
        String last_name;
        if (jsonNode.get("last_name") != null) {
            last_name = jsonNode.get("last_name").asText();
        } else {
            last_name = jsonNode_guests.get("last_name").toString();
        }

        LocalDate arrival_date = LocalDate.parse(jsonNode.get("arrival_date").asText());

        LocalDate departure_date = LocalDate.parse(jsonNode.get("departure_date").asText());

        return new Reservation(
                reservation_id,
                hotel_chain,
                hotel_room,
                first_name.replace("\"",""),
                last_name.replace("\"",""),
                arrival_date,
                departure_date
        );
    }

    public static String formFullURLWithPort(int port, String uri) {
        return "http://localhost:" + port + "/api/reservations" + uri;
    }


}