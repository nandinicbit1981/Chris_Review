package christopher.popularmovies.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import christopher.popularmovies.Model.Movie;

public class MainViewModel extends AndroidViewModel {


    private static final String LOG_TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    private Database database;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = Database.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Actively retreiving the movies from the database");
        movies = database.daoAccess().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Movie> getMovieById(Integer id) {
        return database.daoAccess().getMovie(id);
    }
}


