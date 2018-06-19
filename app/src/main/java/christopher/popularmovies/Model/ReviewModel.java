package christopher.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("key")
    private String trailerKey;
    @SerializedName("author")
    private String reviewAuthor;

    @SerializedName("content")
    private String reviewContent;


    public ReviewModel() {

    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
