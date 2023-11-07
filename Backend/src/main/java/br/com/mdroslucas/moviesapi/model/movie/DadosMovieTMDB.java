package br.com.mdroslucas.moviesapi.model.movie;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public record DadosMovieTMDB(
        String title,
        String overview,
        @SerializedName("release_date")
        String releaseDate,
        @SerializedName("backdrop_path")
        String backDropPath,

        @SerializedName("vote_average")
        double voteAverage
) {
}
