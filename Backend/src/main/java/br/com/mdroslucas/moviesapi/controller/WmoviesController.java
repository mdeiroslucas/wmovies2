package br.com.mdroslucas.moviesapi.controller;


import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import br.com.mdroslucas.moviesapi.model.movie.Movie;
import br.com.mdroslucas.moviesapi.model.movie.ResponseMovie;
import br.com.mdroslucas.moviesapi.services.HttpService;
import com.google.gson.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/wmovies")
@CrossOrigin( origins = "*")
public class WmoviesController {

    @Value("${api.url}")
    private String uri;

    @Value("${api.key}")
    private String apiKey;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Autowired
    private HttpService httpService;

    @GetMapping("/now")
    @ResponseStatus(HttpStatus.OK)
    public List<DadosMovieTMDB> getMoviesPlayingNow () throws IOException, InterruptedException {
        return httpService.getMoviesPlayingNow();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DadosMovieTMDB getMovieById(@PathVariable("id") String id) throws IOException, InterruptedException {

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uri + "/movie/"+ id +"?language=pt-br"))
//                .GET()
//                .headers("authorization", apiKey, "accept", "application/json")
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        DadosMovieTMDB dadosMovieTMDB = gson.fromJson(response.body(), DadosMovieTMDB.class);

        return httpService.getMovieById(id);
    }



    @GetMapping("/search/{movieName}")
    public List<DadosMovieTMDB> searchMovieByName(@PathVariable String movieName) throws IOException, InterruptedException {

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uri + "/search/movie?query="+ movieName +"&include_adult=true&language=pt-br"))
//                .GET()
//                .headers("authorization", apiKey, "accept", "application/json")
//                .build();
//
//        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.body());
//
//        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
//
//        JsonArray resultsArray = jsonObject.get("results").getAsJsonArray();
//
//        for (JsonElement movieElement : resultsArray) {
//            DadosMovieTMDB dadosMovie = getDadosMovieTMDB(movieElement);
//            System.out.println(dadosMovie);
//            dadosMoviesList.add(dadosMovie);
//        }
        return httpService.searchMovieByName(movieName);
    }

    private static DadosMovieTMDB getDadosMovieTMDB(JsonElement movieElement) {
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
}
