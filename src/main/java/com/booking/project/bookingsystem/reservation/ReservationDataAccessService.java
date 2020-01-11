package com.booking.project.bookingsystem.reservation;

import com.booking.project.bookingsystem.reservation.model.HotelChain;
import com.booking.project.bookingsystem.reservation.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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
                reservation.getHotel_chain().name(),
                reservation.getHotel_room().getRoom(),
                reservation.getGuest().getFirst_name(),
                reservation.getGuest().getLast_name(),
                reservation.getArrival_date(),
                reservation.getDeparture_date()
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

        //deleteAllReservationsThatIsOutDated();// maybe later!
        return jdbcTemplate.query(sql, mapReservationsFromDb());
    }

    private RowMapper<Reservation> mapReservationsFromDb() {
        return (resultSet, i) -> {
            String reservationIdStr = resultSet.getString("reservation_id");
            UUID reservationId = UUID.fromString(reservationIdStr);

            HotelChain hotel_chain = HotelChain.valueOf(resultSet.getString("hotel_chain"));
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");

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

    public Reservation fetchReservationById(UUID reservation_id) {
        String sql = "SELECT " +
                " reservation_id, " +
                " hotel_chain, " +
                " hotel_room, " +
                " first_name, " +
                " last_name, " +
                " arrival_date, " +
                " departure_date" +
                " FROM reservation " +
                " WHERE reservation_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{reservation_id}, mappingReservationByIdFromDb());
    }

    private RowMapper<Reservation> mappingReservationByIdFromDb() {
        return ((resultSet, i) -> new Reservation(
                UUID.fromString(resultSet.getString(("reservation_id"))),
                HotelChain.valueOf(resultSet.getString("hotel_chain")),
                Integer.parseInt(resultSet.getString("hotel_room")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                LocalDate.parse(resultSet.getString("arrival_date")),
                LocalDate.parse(resultSet.getString("departure_date"))
        ));
    }

    @SuppressWarnings("ConstantCondtitions")
    public boolean isThatReservationDateForThatRoomTaken(Reservation reservation) {

        List<Reservation> sortedReservationByHotelName = getSortedReservationByHotel(reservation.getHotel_chain());

        for (Reservation reservations : sortedReservationByHotelName) {

            if (reservation.getHotel_room().getRoom() == reservations.getHotel_room().getRoom()) {
                if (reservation.getArrival_date().isEqual(reservations.getArrival_date())
                        && reservation.getDeparture_date().isEqual(reservations.getDeparture_date()))
                    return true;

                if (reservation.getArrival_date().isAfter(reservations.getArrival_date())
                        && reservation.getDeparture_date().isBefore(reservations.getDeparture_date()))
                    return true;

                if (reservation.getArrival_date().isBefore(reservations.getArrival_date())
                        && (reservation.getDeparture_date().isAfter(reservations.getDeparture_date())
                        || reservation.getDeparture_date().isEqual(reservations.getDeparture_date())))
                    return true;

                if (reservation.getArrival_date().isAfter(reservations.getArrival_date())
                        && reservation.getArrival_date().isBefore(reservations.getDeparture_date()))
                    return true;

                if (reservation.getDeparture_date().isAfter(reservations.getArrival_date())
                        && reservation.getDeparture_date().isBefore(reservations.getDeparture_date()))
                    return true;
            }
        }

        return false;
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


    public void deleteAllReservationsThatIsOutDated() {
        String sql = "" +
                "DELETE FROM reservation " +
                "WHERE departure_date < NOW()";
        jdbcTemplate.update(sql);
    }

    List<Reservation> getSortedReservationByHotel(HotelChain hotel_chain) {
        String sql = "SELECT " +
                " reservation_id, " +
                " hotel_chain, " +
                " hotel_room, " +
                " first_name, " +
                " last_name, " +
                " arrival_date, " +
                " departure_date" +
                " FROM reservation " +
                " WHERE hotel_chain = ?";

        return jdbcTemplate.query(sql, new Object[]{hotel_chain.toString()}, sortedHotelRowMapping());
    }

    private RowMapper<Reservation> sortedHotelRowMapping() {
        return ((resultSet, i) -> new Reservation(
                UUID.fromString(resultSet.getString(("reservation_id"))),
                HotelChain.valueOf(resultSet.getString("hotel_chain")),
                Integer.parseInt(resultSet.getString("hotel_room")),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                LocalDate.parse(resultSet.getString("arrival_date")),
                LocalDate.parse(resultSet.getString("departure_date"))
        ));
    }

    public void deleteAllReservation() {
        String sql =""+
                " DELETE FROM reservation ";
        jdbcTemplate.update(sql);
    }
}
