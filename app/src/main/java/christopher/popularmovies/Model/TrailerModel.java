package christopher.popularmovies.Model;

import com.google.gson.annotations.SerializedName;

public class TrailerModel {

    @SerializedName("key")
    private String trailerKey;
    @SerializedName("name")
    private String trailerName;

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public TrailerModel() {

    }

}
