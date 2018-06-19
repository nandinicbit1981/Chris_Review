package christopher.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static christopher.popularmovies.Data.MovieContract.MovieEntry.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {

    //will access whole directory of movies
    public static final int MOVIES = 100;

    //will access single row of a movie
    public static final int MOVIES_ID = 101;

    //member variable of returned uriMatcher from buildUriMatcher method
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIES_ID);

        return uriMatcher;
    }

    private MovieDbHelper movieDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        movieDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor = db.query(TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;

            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(TABLE_NAME,
                        strings,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        s1);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
