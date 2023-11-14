package br.com.mdroslucas.moviesapi.model.movie;


import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public record DadosMovieTMDB(
        Integer id,
        String title,
        String overview,
        @SerializedName("release_date")
        String releaseDate,
        @SerializedName("backdrop_path")
        String backDropPath,

        @SerializedName("vote_average")
        double voteAverage,

        @SerializedName("poster_path")
        String posterPath
) {
}
