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

    @Query("SELECT * FROM earthquake WHERE published_date = :date")
    List<Earthquake> getEarthquakesByDate(String date);

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
}
