package org.me.gcu.equakestartercode.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.me.gcu.equakestartercode.dao.EarthquakeDao;
import org.me.gcu.equakestartercode.models.Earthquake;

@Database(entities = {Earthquake.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EarthquakeDao earthquakeDao();
}
