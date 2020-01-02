package com.booking.project.bookingsystem.reservations;

import com.booking.project.bookingsystem.reservations.model.HotelChain;
import com.booking.project.bookingsystem.reservations.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("bookingsystem")
public class ReservationDataAccessService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertReservation(UUID reservation_id, Reservation reservation) {
        final String sql = ""
                + "INSERT INTO reservation (" +
                " reservation_id, " +
                " hotel_chain, " +
                " hotel_room, " +
                " first_name, " +
                " last_name, " +
                " arrival_date, " +
                " departure_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                reservation_id,
                reservation.getHotelChain().name(),
                reservation.getRoom().getRoom(),
                reservation.getGuest().getFirst_name(),
                reservation.getGuest().getLast_name(),
                reservation.getArrivalDate(),
                reservation.getDepartureDate()
        );
    }

    public List<Reservation> selectAllReservations() {
        String sql = "" +
                "SELECT " +
                " reservation_id, " +
                " hotel_chain, " +
                " hotel_room, " +
                " first_name, " +
                " last_name, " +
                " arrival_date, " +
                " departure_date" +
                " FROM reservation";

        return jdbcTemplate.query(sql, mapReservationsFromDb());
    }

    private RowMapper<Reservation> mapReservationsFromDb() {
        return (resultSet, i) -> {
            String reservationIdStr = resultSet.getString("reservation_id");
            UUID reservationId = UUID.fromString(reservationIdStr);

            HotelChain hotel_chain = HotelChain.valueOf(resultSet.getString("hotel_chain"));
            String first_name = resultSet.getString("first_name").toUpperCase();
            String last_name = resultSet.getString("last_name").toUpperCase();

            String hotel_roomStr = resultSet.getString("hotel_room");
            int hotel_room = Integer.parseInt(hotel_roomStr);

            String arrival_dateStr = resultSet.getString("arrival_date");
            String departure_dateStr = resultSet.getString("departure_date");
            LocalDate arrival_date = LocalDate.parse(arrival_dateStr);
            LocalDate departure_date = LocalDate.parse(departure_dateStr);

            return new Reservation(
                    reservationId,
                    hotel_chain,
                    hotel_room,
                    first_name,
                    last_name,
                    arrival_date,
                    departure_date
            );
        };
    }

    @SuppressWarnings("ConstantCondtitions")
    public boolean isReservationDateTaken(LocalDate arrivalDate, LocalDate departureDate) {

        String sql1 = "" +
                "SELECT EXISTS ( " +
                " SELECT 1 " +
                " FROM reservation " +
                " WHERE arrival_date = ?" +
                ")";
        boolean arrival_date = Optional.ofNullable(jdbcTemplate.queryForObject(
                sql1, new Object[]{arrivalDate},
                (resultSet, i) -> resultSet.getBoolean(1)))
                .orElseThrow();

        String sql2 = "" +
                "SELECT EXISTS ( " +
                " SELECT 1 " +
                " FROM reservation " +
                " WHERE arrival_date = ?" +
                ")";
        boolean departure_date = Optional.ofNullable(jdbcTemplate.queryForObject(
                sql1, new Object[]{departureDate},
                (resultSet, i) -> resultSet.getBoolean(1)))
                .orElseThrow();

        return arrival_date || departure_date;
    }

    public void deleteReservationById(UUID reservation_id) {
        String sql = "" +
                " DELETE FROM reservation" +
                " WHERE reservation_id = ?";

        jdbcTemplate.update(sql, reservation_id);
    }


    public int updateHotelChain(UUID reservation_id, HotelChain hotel_chain) {
        String sql = "" +
                " UPDATE reservation " +
                " SET hotel_chain = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, hotel_chain, reservation_id);
    }

    public int updateHotelRoom(UUID reservation_id, Integer room_number) {
        String sql = "" +
                " UPDATE reservation " +
                " SET hotel_room = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, room_number, reservation_id);
    }

    public int updateFirstName(UUID reservation_id, String first_name) {
        String sql = "" +
                " UPDATE reservation " +
                " SET first_name = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, first_name, reservation_id);
    }

    public int updateLastName(UUID reservation_id, String last_name) {
        String sql = "" +
                " UPDATE reservation " +
                " SET last_name = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, last_name, reservation_id);
    }

    public void updateArrivalDate(UUID reservation_id, LocalDate arrival_date) {
        String sql = "" +
                " UPDATE reservation " +
                " SET arrival_date = ?" +
                " WHERE reservation_id = ?";
        jdbcTemplate.update(sql, arrival_date, reservation_id);
    }

    public void updateDepartureDate(UUID reservation_id, LocalDate departure_date) {
        String sql = "" +
                " UPDATE reservation " +
                " SET departure_date = ?" +
                " WHERE reservation_id = ?";
        jdbcTemplate.update(sql, departure_date, reservation_id);
    }

    public boolean isReservationDateBetweenReservations(UUID reservation_id, Reservation reservation) {
        /*TODO:
         *  1) list all arrival_dates and departure_dates for DB that has hotel_brand and hotel_room
         *  2) sort the list by arrival_date
         *  3) check if the asked reservation date is taken return true (modelling if-stats)
         *  4) if date is good return false
         * */


        return false;
    }
}
