package christopher.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import christopher.popularmovies.Data.Database;
import christopher.popularmovies.Data.MainViewModel;
import christopher.popularmovies.Data.MovieContract;
import christopher.popularmovies.Model.Movie;
import christopher.popularmovies.Model.ReviewModel;
import christopher.popularmovies.Model.ReviewResponse;
import christopher.popularmovies.Model.TrailerModel;
import christopher.popularmovies.Model.TrailerResponse;
import christopher.popularmovies.Utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = "101";

    @BindView(R.id.poster_image)
    ImageView posterImage;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.rating)
    TextView userRating;
    @BindView(R.id.releasedate)
    TextView releaseDate;
    @BindView(R.id.overview)
    TextView overview;
    TextView poster;

    @BindView(R.id.favorite_button)
    FloatingActionButton favorite;

    int id;
    String title;
    String image;
    String synopsis;
    Double rating;
    String release;
    String userReviews;
    int idTrailer;
    int idReview;
    int idContentReview;


    Boolean isFavorite = false;

    Movie movie;


    private List<Movie> movieList;

    private RecyclerView trailerRecyclerView;
    private TrailerAdapter adapter;
    private List<TrailerModel> trailerModelList;


    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewModelList;


    private Database movieDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        Intent intentFromMainActivity = getIntent();

        id = (getIntent().getExtras().getInt("id"));
        title = getIntent().getExtras().getString("title");
        String[] images = getIntent().getExtras().getString("image").split("/");
        image = "/"+images[images.length - 1];
        synopsis = getIntent().getExtras().getString("overview");
        rating = getIntent().getExtras().getDouble("average_rating");
        release = getIntent().getExtras().getString("release");

        idTrailer = getIntent().getExtras().getInt("trailerId");
        idReview = getIntent().getExtras().getInt("reviewId");
        idContentReview = getIntent().getExtras().getInt("reviewContentId");


        movieDatabase = Database.getInstance(getApplicationContext());


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(posterImage);

        movieTitle.setText(title);
        releaseDate.setText(release);
        userRating.setText(rating.toString() + " / 10");
        overview.setText(synopsis);

        movie = new Movie(id, title, image, synopsis, rating, release);

        loadViews();

        loadViewsReview();

        parseJson();

        parseJsonReview();
        setUpViewModel();


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadViews();
    }

    private void loadViews() {
        trailerModelList = new ArrayList<>();

        adapter = new TrailerAdapter(this, trailerModelList);
        if(movie.isFavorite()) {
            favorite.setBackground(getResources().getDrawable(R.drawable.favoritefilled));
        }
        trailerRecyclerView = (RecyclerView) (findViewById(R.id.rv_trailers));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void parseJson() {
        RetrofitClient client = new RetrofitClient();

        ApiService apiService = client.getClient().create(ApiService.class);

        Call<TrailerResponse> call = apiService.getTrailer(idTrailer, "de9c335bcf10921c29babb85a73c47dd");
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<TrailerModel> trailer = response.body().getTrailerResults();
                trailerRecyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
                trailerRecyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, R.string.internet_connect_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadViewsReview() {
        reviewModelList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewModelList);

        reviewRecyclerView = (RecyclerView) (findViewById(R.id.rv_reviews));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerView.setAdapter(adapter);
        reviewAdapter.notifyDataSetChanged();
    }

    private void parseJsonReview() {
        RetrofitClient client = new RetrofitClient();

        ApiService apiService = client.getClient().create(ApiService.class);
        Call<ReviewResponse> call = apiService.getReviews(idReview, "de9c335bcf10921c29babb85a73c47dd");
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                List<ReviewModel> review = response.body().getReviewResults();
                reviewRecyclerView.setAdapter(new ReviewAdapter(getApplicationContext(), review));
                reviewRecyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, R.string.internet_connect_error, Toast.LENGTH_LONG).show();
            }
        });

    }

    public Cursor loadInBackground() {
        try {
            return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    private void setUpViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {


            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "Updating movies from LiveData in ViewModel");
                adapter.notifyDataSetChanged();
            }
        });


    }


    public void saveToFavorites(View v) {

        if (!isFavorite) {
            movie.setFavorite(true);
            favorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favoritefilled));
            Toast.makeText(getApplicationContext(), R.string.add_favorite, Toast.LENGTH_SHORT).show();

            isFavorite = true;
            new Task2().execute("INSERT");

        } else if (isFavorite = true) {
            favorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite));
            Toast.makeText(getApplicationContext(), R.string.delete_favorite, Toast.LENGTH_SHORT).show();

            isFavorite = false;
            new Task2().execute("DELETE");
        }

    }

    class Task2 extends AsyncTask<String, Void, Void>

    {
        @Override
        protected Void doInBackground(String... strings) {
            if(strings[0].equals("INSERT")) {
                movieDatabase.daoAccess().insertMovie(movie);
            } else{
                movieDatabase.daoAccess().deleteMovie(movie);
            }

            return null;
        }
    }


}









