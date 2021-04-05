package org.me.gcu.equakestartercode.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.me.gcu.equakestartercode.models.Earthquake;

import java.util.List;

@Dao
public interface EarthquakeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Earthquake earthquake);

    @Query("SELECT * FROM earthquake")
    List<Earthquake> getEarthquakes();
}
