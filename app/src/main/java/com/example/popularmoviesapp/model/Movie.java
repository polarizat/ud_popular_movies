package com.example.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public static final String KEY_MOVIE = "movie";

    private String id;
    private String originalTitle;
    private String posterPath;
    private String releaseDate;
    private String overview;
    private String originalLanguage;
    private String voteAverage;

    public Movie(String id, String originalTitle, String posterPath, String releaseDate, String overview, String originalLanguage, String voteAverage) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
    }

    public String getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    private Movie(Parcel in){
        id = in.readString();
        originalTitle = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        originalLanguage = in.readString();
        voteAverage = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };


}
