package christopher.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import christopher.popularmovies.Data.Database;
import christopher.popularmovies.Data.MainViewModel;
import christopher.popularmovies.Model.ApiResponse;
import christopher.popularmovies.Model.Movie;
import christopher.popularmovies.Utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    private MovieAdapter mAdapter;

    private RecyclerView recyclerView;
    private List<Movie> movieList;

    private static String TAG = "101";

    private Database movieDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        sorting();

        setUpViewModel();

        sortByFavorite();
    }


    private void loadViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

        movieList = new ArrayList<>();
        mAdapter = new MovieAdapter(this, movieList);

        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 4);
            recyclerView.setLayoutManager(layoutManager);
        }


        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void parseJson() {

        RetrofitClient client = new RetrofitClient();

        ApiService apiService = client.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.getMovies("de9c335bcf10921c29babb85a73c47dd");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                List<Movie> movies = response.body().getResults();


                recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                recyclerView.smoothScrollToPosition(0);

                Toast.makeText(MainActivity.this, R.string.sort_popularity, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.internet_connect_error, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;


        }
        return super.onOptionsItemSelected(item);

    }

    private void parseJsonHighestRated() {

        RetrofitClient client = new RetrofitClient();

        ApiService apiService = client.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.getTopMovies("de9c335bcf10921c29babb85a73c47dd");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                List<Movie> movies = response.body().getResults();


                recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                recyclerView.smoothScrollToPosition(0);

                Toast.makeText(MainActivity.this, R.string.sort_toprated, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.internet_connect_error, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        sorting();
    }

    private void sorting() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String order = sharedPreferences.getString(
                this.getString(R.string.preference_order_key),
                this.getString(R.string.preference_popular)
        );
        if (order.equals(this.getString(R.string.preference_popular))) {

            parseJson();
        } else if (order.equals(this.getString(R.string.preference_rating))) {

            parseJsonHighestRated();
        } else if (order.equals(this.getString(R.string.preference_favorites))) {
            sortByFavorite();
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void sortByFavorite() {
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {


            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieList = movies;
                Log.d(TAG, "Showing list of favorite movies");
                mAdapter.setMovies((ArrayList<Movie>) movies);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }

    private void setUpViewModel() {
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {


            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieList = movies;
                Log.d(TAG, "Updating movies from LiveData in ViewModel");
                mAdapter.setMovies((ArrayList<Movie>) movies);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sorting();

        setUpViewModel();

        sortByFavorite();
    }
}






