package christopher.popularmovies.Data;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import christopher.popularmovies.Model.Movie;


@android.arch.persistence.room.Database(entities = {Movie.class}, version = 2, exportSchema = false)

public abstract class Database extends RoomDatabase {

    private static final String LOG_TAG = Database.class.getSimpleName();

    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "moviesdb";

    private static Database sInstance;

    public static Database getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        Database.class, Database.DATABASE_NAME)
                        //.allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Creating new database instance");
        return sInstance;
    }

    public abstract DaoAccess daoAccess();
}

