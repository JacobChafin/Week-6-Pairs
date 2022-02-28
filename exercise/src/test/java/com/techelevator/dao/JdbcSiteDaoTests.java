package com.techelevator.dao;

import com.techelevator.model.Site;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JdbcSiteDaoTests extends BaseDaoTests {

    private static final Site SITE_1 = new Site(1, 1, 1, 10, true, 33, true);
    private static final Site SITE_2 = new Site(2, 1, 2, 10, true, 30, true);
    private static final Site SITE_3 = new Site(3, 1, 3, 10, true, 0, true);

    private SiteDao dao;

    @Before
    public void setup() {
        dao = new JdbcSiteDao(dataSource);
    }

    @Test
    public void getSitesThatAllowRVs_Should_ReturnSites() {
        // Arrange
        int testParkId = 1;
        int expectedSize = 2;

        // Act
        List<Site> sites = dao.getSitesThatAllowRVs(testParkId);
        int actualSize = sites.size();

        // Assert
        assertEquals(expectedSize, actualSize);
        assertSitesMatch(SITE_1, sites.get(0));
        assertSitesMatch(SITE_2, sites.get(1));
    }

    @Test
    public void getAvailableSites_Should_ReturnSites() {
        // Arrange
        int expectedSize = 2;
        int testParkId = 1;

        // Act
        List<Site> actual = dao.getAvailableSites(testParkId);
        int actualSize = actual.size();

        // Assert
        Assert.assertEquals(expectedSize, actualSize);
        assertSitesMatch(SITE_2, actual.get(0));
        assertSitesMatch(SITE_3, actual.get(1));
    }

    @Test
    public void getAvailableSitesDateRange_Should_ReturnSites() {
        // Arrange
        int expectedSize = 2;

        // Act
        List<Site> sites = dao.getAvailableSitesDateRange(1, LocalDate.now().plusDays(3), LocalDate.now().plusDays(5));
        int actualSize = sites.size();

        // Assert
        Assert.assertEquals(expectedSize, actualSize);
        assertSitesMatch(SITE_2, sites.get(0));
        assertSitesMatch(SITE_3, sites.get(1));
    }

    private void assertSitesMatch(Site expected, Site actual) {
        assertEquals(expected.getSiteId(), actual.getSiteId());
        assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
        assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
        assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
        assertEquals(expected.isAccessible(), actual.isAccessible());
        assertEquals(expected.getMaxRvLength(), actual.getMaxRvLength());
        assertEquals(expected.isUtilities(), actual.isUtilities());
    }
}
