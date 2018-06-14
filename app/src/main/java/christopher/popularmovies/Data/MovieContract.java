package christopher.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static String AUTHORITY = "christopher.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "moviestable";
        public static final String COLUMN_MOVIE_ID = "movieid";
        public static final String COLUMN_MOVIE_TITLE = "movietitle";
        public static final String COLUMN_RATING = "movierating";
        public static final String COLUMN_POSTER = "movieposter";
        public static final String COLUMN_SYNOPSIS = "moviesynopsis";
        public static final String COLUMN_RELEASE_DATE = "moviereleasedate";
    }
}
