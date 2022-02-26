package com.techelevator.dao;

import com.techelevator.model.Reservation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertEquals;

public class JdbcReservationDaoTests extends BaseDaoTests {

     private static final Reservation RESERVATION_1 = new Reservation(1,1, "Test Testerson",
             LocalDate.now().plus(1, DAYS), LocalDate.now().plus(5, DAYS), LocalDate.now().minus(23, DAYS));

    private static final Reservation RESERVATION_2 = new Reservation(2,1, "Bob Robertson",
            LocalDate.now().plus(11, DAYS), LocalDate.now().plus(18, DAYS), LocalDate.now().minus(23, DAYS));

    private static final Reservation RESERVATION_3 = new Reservation(3,1, "Manager Managerson",
            LocalDate.now().minus(5, DAYS), LocalDate.now().plus(2, DAYS), LocalDate.now().minus(23, DAYS));

    private static final Reservation RESERVATION_4 = new Reservation(4,1, "Leonard Leonardson",
            LocalDate.now().minus(11, DAYS), LocalDate.now().minus(18, DAYS), LocalDate.now().minus(23, DAYS));

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

        //Arrange
        int expectedSize = 2;

        //Act
        List<Reservation> actualReservations = dao.getUpcomingReservationsByParkId(1);
        //Assert
        Assert.assertEquals(expectedSize, actualReservations.size());
        assertReservationsMatch(RESERVATION_1, actualReservations.get(0));
        assertReservationsMatch(RESERVATION_2, actualReservations.get(1));

    }

    // helper method to compare two reservations
    private void assertReservationsMatch(Reservation expected, Reservation actual) {
        Assert.assertEquals(expected.getReservationId(), actual.getReservationId());
        Assert.assertEquals(expected.getSiteId(), actual.getSiteId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getFromDate(), actual.getFromDate());
        Assert.assertEquals(expected.getToDate(), actual.getToDate());
        Assert.assertEquals(expected.getCreateDate(), actual.getCreateDate());
    }
}
