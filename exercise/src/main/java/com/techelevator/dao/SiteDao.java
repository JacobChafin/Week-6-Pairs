package com.techelevator.dao;

import com.techelevator.model.Site;

import java.time.LocalDate;
import java.util.List;

public interface SiteDao {

    List<Site> getSitesThatAllowRVs(int parkId);

    // Also Checks overlap of current guests with reservations
    List<Site> getAvailableSites(int parkId);
}
