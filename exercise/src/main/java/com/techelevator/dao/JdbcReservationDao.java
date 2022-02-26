package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcReservationDao implements ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int createReservation(int siteId, String name, LocalDate fromDate, LocalDate toDate) {
        // Null checking?!
        String sql = "INSERT INTO reservation (site_id, name, from_date, to_date) VALUES (?, ?, ?, ?) RETURNING reservation_id;";
        return jdbcTemplate.queryForObject(sql, Integer.class, siteId, name, fromDate, toDate);
    }

    @Override
    public List<Reservation> getUpcomingReservationsByParkId(int parkId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT reservation_id, reservation.site_id, reservation.name, from_date, to_date, create_date " +
                "FROM reservation " +
                "JOIN site ON site.site_id = reservation.site_id " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "WHERE park_id = ? AND from_date >= CURRENT_DATE AND from_date <= CURRENT_DATE + 30 " +
                "ORDER BY reservation_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        while (results.next()) {
            Reservation reservation = mapRowToReservation(results);
            reservations.add(reservation);
        }
        return reservations;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation r = new Reservation();
        r.setReservationId(results.getInt("reservation_id"));
        r.setSiteId(results.getInt("site_id"));
        r.setName(results.getString("name"));
        r.setFromDate(results.getDate("from_date").toLocalDate());
        r.setToDate(results.getDate("to_date").toLocalDate());
        r.setCreateDate(results.getDate("create_date").toLocalDate());
        return r;
    }
}
