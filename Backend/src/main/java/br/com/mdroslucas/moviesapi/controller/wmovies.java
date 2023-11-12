package br.com.mdroslucas.moviesapi.controller;


import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/wmovies")
@CrossOrigin( origins = "*")
public class wmovies {

    @Value("${api.url}")
    private String uri;

    @Value("${api.key}")
    private String apiKey;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final Type listType = new TypeToken<List<DadosMovieTMDB>>(){}.getType();

    @GetMapping("/now")
    @ResponseStatus(HttpStatus.OK)
    public List<DadosMovieTMDB> dadosMovice () throws IOException, InterruptedException {

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

    @GetMapping("/{id}")
    public DadosMovieTMDB dadosMovie(@PathVariable("id") String id) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/movie/"+ id +"?language=pt-br"))
                .GET()
                .headers("authorization", apiKey, "accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        DadosMovieTMDB dadosMovieTMDB = gson.fromJson(response.body(), DadosMovieTMDB.class);

        return dadosMovieTMDB;
    }
}
