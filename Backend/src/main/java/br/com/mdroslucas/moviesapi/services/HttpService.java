package br.com.mdroslucas.moviesapi.services;

import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private DadosMovieTMDB dadosMovieTMDB;
    private final Gson gson = new Gson();
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
            DadosMovieTMDB dadosMovie = getDadosMovieTMDB(movieElement);
            dadosMoviesList.add(dadosMovie);
        }
        return dadosMoviesList;
    }

    public DadosMovieTMDB getMovieById(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/movie/"+ id +"?language=pt-br"))
                .GET()
                .headers("authorization", apiKey, "accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        DadosMovieTMDB dadosMovieTMDB = gson.fromJson(response.body(), DadosMovieTMDB.class);

        return dadosMovieTMDB;
    }

    public List<DadosMovieTMDB> searchMovieByName(String movieName) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/search/movie?query="+ movieName +"&include_adult=true&language=pt-br"))
                .GET()
                .headers("authorization", apiKey, "accept", "application/json")
                .build();

        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        JsonArray resultsArray = jsonObject.get("results").getAsJsonArray();

        for (JsonElement movieElement : resultsArray) {
            DadosMovieTMDB dadosMovie = getDadosMovieTMDB(movieElement);
            dadosMoviesList.add(dadosMovie);
        }
        return dadosMoviesList;
    }
    private DadosMovieTMDB getDadosMovieTMDB(JsonElement movieElement) {
        JsonObject movieObject = movieElement.getAsJsonObject();
        DadosMovieTMDB dadosMovie = new DadosMovieTMDB(
                movieObject.get("id").getAsInt(),
                movieObject.get("title").getAsString(),
                movieObject.get("overview").getAsString(),
                movieObject.get("release_date").getAsString(),
                movieObject.get("backdrop_path").isJsonNull() ? null : movieObject.get("backdrop_path").getAsString(),
                movieObject.get("vote_average").getAsDouble(),
                movieObject.get("poster_path").isJsonNull() ? null : movieObject.get("poster_path").getAsString()
        );
        return dadosMovie;
    }

    public List<DadosMovieTMDB> getSimilarMovies(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/movie/" + id + "/similar?language=pt-br"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("authorization", apiKey)
                .header("accept", "application/json")
                .build();

        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        JsonArray resultsArray = jsonObject.get("results").getAsJsonArray();

        for (JsonElement movieElement : resultsArray) {
            DadosMovieTMDB dadosMovie = getDadosMovieTMDB(movieElement);
            dadosMoviesList.add(dadosMovie);
        }

        dadosMoviesList.forEach(System.out::println);

        return dadosMoviesList;
    }
}
