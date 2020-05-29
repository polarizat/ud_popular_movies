package com.example.popularmoviesapp.data.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieEntry implements Parcelable {

    /* TMDB Model - Results fields */
    public static final String TMDBM_MOVIE_ID = "id";
    public static final String TMDBM_ORIGINAL_TITLE = "original_title";
    public static final String TMDBM_POSTER_PATH = "poster_path";
    public static final String TMDBM_RELEASE_DATE = "release_date";
    public static final String TMDBM_OVERVIEW = "overview";
    public static final String TMDBM_LANGUAGE = "original_language";
    public static final String TMDBM_VOTE_AVERAGE = "vote_average";

    public static final String KEY_MOVIE = "movie";

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = TMDBM_MOVIE_ID)
    private String id;
    @ColumnInfo(name = TMDBM_ORIGINAL_TITLE)
    private String originalTitle;
    @ColumnInfo(name = TMDBM_POSTER_PATH)
    private String posterPath;
    @ColumnInfo(name = TMDBM_RELEASE_DATE)
    private String releaseDate;
    @ColumnInfo(name = TMDBM_OVERVIEW)
    private String overview;
    @ColumnInfo(name = TMDBM_VOTE_AVERAGE)
    private String voteAverage;

    public MovieEntry(String id, String originalTitle, String posterPath, String releaseDate, String overview, String voteAverage) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
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

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    @Ignore
    private MovieEntry(Parcel in){
        id = in.readString();
        originalTitle = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
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
        dest.writeString(voteAverage);
    }

    public static final Parcelable.Creator<MovieEntry> CREATOR = new Parcelable.Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel parcel) {
            return new MovieEntry(parcel);
        }

        @Override
        public MovieEntry[] newArray(int i) {
            return new MovieEntry[i];
        }

    };


}
