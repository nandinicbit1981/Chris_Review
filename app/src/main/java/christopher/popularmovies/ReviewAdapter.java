package christopher.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import christopher.popularmovies.Model.ReviewModel;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;


    private List<ReviewModel> reviewList;

    public ReviewAdapter(Context mContext, List<ReviewModel> reviewList) {

        this.mContext = mContext;
        this.reviewList = reviewList;
    }


    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, int position) {
        holder.reviewBody.setText(reviewList.get(position).getReviewAuthor());
        holder.reviewContent.setText(reviewList.get(position).getReviewContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reviewBody;

        TextView reviewContent;

        public MyViewHolder(View itemView) {
            super(itemView);

            reviewBody = (TextView) itemView.findViewById(R.id.user_review);
            reviewContent = (TextView) itemView.findViewById(R.id.review_contents);


        }

    }
}
