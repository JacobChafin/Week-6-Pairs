package com.techelevator.dao;

import com.techelevator.model.Site;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        List<Site> sites = dao.getSitesThatAllowRVs(1);

        assertEquals(2,sites.size());
    }

    @Test
    public void getAvailableSites_Should_ReturnSites() {
        // Arrange
        int expectedSize = 2;

        // Act
        List<Site> actual = dao.getAvailableSites(1);
        int actualSize = actual.size();

        // Assert
        Assert.assertEquals(expectedSize, actualSize);
        assertSitesMatch(SITE_2, actual.get(0));
        assertSitesMatch(SITE_3, actual.get(1));
    }

    public void getAvailableSitesDateRange_Should_ReturnSites() {

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
