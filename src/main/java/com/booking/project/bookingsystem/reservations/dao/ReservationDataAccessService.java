package com.booking.project.bookingsystem.reservations.dao;

import com.booking.project.bookingsystem.reservations.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*Data base command for insert, get, post, delete...*/
@Repository("bookingsystem")
public class ReservationDataAccessService implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertReservation(UUID reservation_id, Reservation reservation) {
        final String sql = ""
                + "INSERT INTO reservation (" +
                " reservation_id, " +
                " hotel_brand, " +
                " hotel_room, " +
                " first_name, " +
                " last_name, " +
                " arrival_date, " +
                " departure_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                reservation_id,
                reservation.getHotel_brand().toUpperCase(),
                reservation.getRoom().getRoom(),
                reservation.getGuest().getFirst_name().toUpperCase(),
                reservation.getGuest().getLast_name().toUpperCase(),
                reservation.getArrivalDate(),
                reservation.getDepartureDate()
        );
    }

    @Override
    public List<Reservation> selectAllReservations() {
        String sql = "" +
                "SELECT " +
                " reservation_id, " +
                " hotel_brand, " +
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

            String brand_name = resultSet.getString("hotel_brand").toUpperCase();
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
                    brand_name,
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
        //fix logic later!
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


    public int updateHotelBrand(UUID reservation_id, String hotel_brand) {
        String sql = "" +
                " UPDATE reservation " +
                " SET hotel_brand = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, hotel_brand, reservation_id);
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
        return jdbcTemplate.update(sql, first_name,reservation_id);
    }

    public int updateLastName(UUID reservation_id, String last_name) {
        String sql = "" +
                " UPDATE reservation " +
                " SET last_name = ?" +
                " WHERE reservation_id = ?";
        return jdbcTemplate.update(sql, last_name, reservation_id);
    }

}
