package org.me.gcu.equakestartercode.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.me.gcu.equakestartercode.models.Earthquake;

import java.util.List;

/**
 * Shirley Ng S1626790
 */
@Dao
public interface EarthquakeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Earthquake earthquake);

    @Query("SELECT * FROM earthquake")
    List<Earthquake> getEarthquakes();

    // Queries for getting the filtered earthquake list based on search criteria
    @Query("SELECT * FROM earthquake WHERE published_date = :date")
    List<Earthquake> getEarthquakesByDate(String date);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate")
    List<Earthquake> getEarthquakesByDateRange(String startDate, String endDate);

    // The queries listed below are for searching earthquakes by date
    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY magnitude DESC LIMIT 1")
    Earthquake getLargestMagnitudeEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY depth ASC LIMIT 1")
    Earthquake getShallowestEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY depth DESC LIMIT 1")
    Earthquake getDeepestEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY geo_latitude DESC LIMIT 1")
    Earthquake getNorthEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY geo_latitude ASC LIMIT 1")
    Earthquake getSouthEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY geo_longitude DESC LIMIT 1")
    Earthquake getEastEarthquake(String date);

    @Query("SELECT * FROM earthquake WHERE published_date = :date ORDER BY geo_longitude ASC LIMIT 1")
    Earthquake getWestEarthquake(String date);

    // The queries below are for searching earthquakes by date range
    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY magnitude DESC LIMIT 1")
    Earthquake getLargestMagnitudeEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY depth ASC LIMIT 1")
    Earthquake getShallowestEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY depth DESC LIMIT 1")
    Earthquake getDeepestEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY geo_latitude DESC LIMIT 1")
    Earthquake getNorthEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY geo_latitude ASC LIMIT 1")
    Earthquake getSouthEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY geo_longitude DESC LIMIT 1")
    Earthquake getEastEarthquakeByDateRange(String startDate, String endDate);

    @Query("SELECT * FROM earthquake WHERE published_date BETWEEN :startDate AND :endDate ORDER BY geo_longitude ASC LIMIT 1")
    Earthquake getWestEarthquakeByDateRange(String startDate, String endDate);
}
