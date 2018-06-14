package christopher.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import christopher.popularmovies.Model.TrailerModel;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private Context mContext;


    private List<TrailerModel> trailerList;

    public TrailerAdapter(Context mContext, List<TrailerModel> trailerList) {

        this.mContext = mContext;
        this.trailerList = trailerList;
    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.MyViewHolder holder, int position) {
        holder.titleForTrailer.setText(trailerList.get(position).getTrailerName());

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleForTrailer;
        ImageView trailerImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleForTrailer = (TextView) itemView.findViewById(R.id.trailer_title);
            trailerImage = (ImageView) itemView.findViewById(R.id.iv_trailer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    TrailerModel clickedItem = trailerList.get(clickedPosition);

                    String trailerId = trailerList.get(clickedPosition).getTrailerKey();

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerId));
                    intent.putExtra("trailerID", trailerId);
                    mContext.startActivity(intent);

                }
            });
        }
    }
}



