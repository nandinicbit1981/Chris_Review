package christopher.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName("id")
    private int trailer_id;
    @SerializedName("results")
    private List<TrailerModel> trailerResults;

    public int getTrailer_id() {
        return trailer_id;
    }

    public void setTrailer_id(int trailer_id) {
        this.trailer_id = trailer_id;
    }

    public List<TrailerModel> getTrailerResults() {
        return trailerResults;
    }


}
