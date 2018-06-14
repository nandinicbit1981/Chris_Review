package christopher.popularmovies.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import christopher.popularmovies.Model.Movie;


@Dao
public interface DaoAccess {

    @Query("SELECT * FROM movie_table ORDER BY popularity")
    LiveData<List<Movie>> getAllMovies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movies);

    @Delete
    void deleteMovie(Movie movies);


}
