package br.com.mdroslucas.moviesapi.services;

import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class HttpService {

    @Value("${api.url}")
    private String uri;

    @Value("${api.key}")
    private String apiKey;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final Type listType = new TypeToken<List<DadosMovieTMDB>>(){}.getType();
    public List<DadosMovieTMDB> getMoviesPlayingNow() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/discover/movie?include_adult=true&language=pt-br"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("authorization", apiKey)
                .header("accept", "application/json")
                .build();

        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        JsonArray resultsArray = jsonObject.get("results").getAsJsonArray();

        for (JsonElement movieElement : resultsArray) {
            JsonObject movieObject = movieElement.getAsJsonObject();
            DadosMovieTMDB dadosMovie = new DadosMovieTMDB(
                    movieObject.get("id").getAsInt(),
                    movieObject.get("title").getAsString(), movieObject.get("overview").getAsString(),
                    movieObject.get("release_date").getAsString(), movieObject.get("backdrop_path").getAsString(),
                    movieObject.get("vote_average").getAsDouble(), movieObject.get("poster_path").getAsString()
            );
            dadosMoviesList.add(dadosMovie);
        }
        return dadosMoviesList;
    }
}
