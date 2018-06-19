package christopher.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import christopher.popularmovies.Model.Movie;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.NumberViewHolder> {


    private Context mContext;


    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList) {

        this.mContext = mContext;
        this.movieList = movieList;
    }


    @Override
    public MovieAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MovieAdapter.NumberViewHolder holder, int position) {


        String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath();


        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.posterthumbnail);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        TextView rating;
        ImageView posterthumbnail;


        public NumberViewHolder(View itemView) {
            super(itemView);

            posterthumbnail = (ImageView) itemView.findViewById(R.id.tv_item_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    Movie clickedItem = movieList.get(clickedPosition);

                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("title", movieList.get(clickedPosition).getOriginalTitle());
                    intent.putExtra("image", "https://image.tmdb.org/t/p/w500" + movieList.get(clickedPosition).getPosterPath());
                    intent.putExtra("overview", movieList.get(clickedPosition).getOverview());
                    intent.putExtra("average_rating", movieList.get(clickedPosition).getVoteAverage());
                    intent.putExtra("release", movieList.get(clickedPosition).getReleaseDate());
                    intent.putExtra("trailerId", movieList.get(clickedPosition).getId());
                    intent.putExtra("reviewId", movieList.get(clickedPosition).getId());
                    intent.putExtra("id", movieList.get(clickedPosition).getId());
                    intent.putExtra("reviewContentId", movieList.get(clickedPosition).getId());


                    mContext.startActivity(intent);

                }
            });
        }

    }

    public void setMovies(List<Movie> movieEntries) {
        movieList = movieEntries;
        notifyDataSetChanged();
    }
}
