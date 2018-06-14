package christopher.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("id")
    private int review_id;
    @SerializedName("results")
    private List<ReviewModel> reviewResults;

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public List<ReviewModel> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<ReviewModel> reviewResults) {
        this.reviewResults = reviewResults;
    }
}
