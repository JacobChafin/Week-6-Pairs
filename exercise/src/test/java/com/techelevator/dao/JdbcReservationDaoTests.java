package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {
//    /* test reservations */
//    /**** future */
//    INSERT INTO reservation(site_id, name, from_date, to_date, create_date)
//    VALUES (1, 'Test Testerson', CURRENT_DATE + 1, CURRENT_DATE + 5, CURRENT_DATE - 23); -- reservation_id will be 1 due to serial
//
//    INSERT INTO reservation(site_id, name, from_date, to_date, create_date)
//    VALUES (1, 'Bob Robertson', CURRENT_DATE + 11, CURRENT_DATE + 18, CURRENT_DATE - 23); -- reservation_id will be 2 due to serial
//
//    /**** present */
//    INSERT INTO reservation(site_id, name, from_date, to_date, create_date)
//    VALUES (1, 'Manager Managerson', CURRENT_DATE - 5, CURRENT_DATE + 2, CURRENT_DATE - 23); -- reservation_id will be 3 due to serial
//
//    /**** past */
//    INSERT INTO reservation(site_id, name, from_date, to_date, create_date)
//    VALUES (1, 'Leonard Leonardson', CURRENT_DATE - 11, CURRENT_DATE - 18, CURRENT_DATE - 23); -- reservation_id will be 4 due to serial

//    private int reservationId;
//    private int siteId;
//    private String name;
//    private LocalDate fromDate;
//    private LocalDate toDate;
//    private LocalDate createDate;

    // private static final Reservation RESERVATION_1 = new Reservation(1, "Test Testerson", )

    private ReservationDao dao;

    @Before
    public void setup() {
        dao = new JdbcReservationDao(dataSource);
    }

    @Test
    public void createReservation_Should_ReturnNewReservationId() {
        int reservationCreated = dao.createReservation(1,
                "TEST NAME",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3));

        assertEquals(5, reservationCreated);
    }

    @Test
    public void getUpcomingReservationsByParkId_returns_correct_number_of_reservations() {

    }

    // helper method to compare two reservations
    private void reservationsMatch(Reservation expected, Reservation actual) {
        Assert.assertEquals(expected.getReservationId(), actual.getReservationId());
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
        Assert.assertEquals(expected.getToDate(), actual.getToDate());
        Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());
    }
}
