package com.techelevator.dao;

import com.techelevator.model.Site;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcSiteDao implements SiteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSiteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Site> getSitesThatAllowRVs(int parkId) {
        List<Site> sites = new ArrayList<>();
        // Get all site ids from park id that allow max_rv_length != 0;
        String sql = "SELECT site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities " +
                "FROM site " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "WHERE park_id = ? AND max_rv_length > 0;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);

        // Iterate over list of campgrounds
        while (results.next()) {
            sites.add(mapRowToSite(results));
        }
        return sites;
    }

    @Override
    public List<Site> getAvailableSites(int parkId) {
        List<Site> sites = new ArrayList<>();

        String sql = "SELECT site.site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities " +
                "FROM campground " +
                "JOIN site ON campground.campground_id = site.campground_id " +
                "LEFT JOIN reservation ON site.site_id = reservation.site_id " +
                "WHERE reservation_id IS NULL AND park_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);
        while (results.next())
            sites.add(mapRowToSite(results));
        return sites;
    }

    @Override
    public List<Site> getAvailableSitesDateRange(int parkId, LocalDate fromDate, LocalDate toDate) {
        // TODO: Null checking?!?!?!?!

        // This would likely be better split into two separate SQL queries for readability
        // Throws out entire site if there is a data entry error
        List<Site> sites = new ArrayList<>();
        String sql = "SELECT site_id, site.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities " +
                "FROM site " +
                "JOIN campground ON site.campground_id = campground.campground_id " +
                "WHERE park_id = ? " +
                "AND site.site_id NOT IN " +
                "(" +
                "SELECT reservation.site_id " +
                "FROM reservation " +
                "JOIN site ON reservation.site_id = site.site_id " +
                "JOIN campground ON campground.campground_id = site.campground_id " +
                "WHERE park_id = ? AND ((? BETWEEN from_date AND to_date) OR ? BETWEEN from_date AND to_date) " +
                "OR from_date > to_date " +
                ");";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId, parkId, fromDate, toDate);
        while (results.next())
            sites.add(mapRowToSite(results));
        return sites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteId(results.getInt("site_id"));
        site.setCampgroundId(results.getInt("campground_id"));
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRvLength(results.getInt("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }
}
