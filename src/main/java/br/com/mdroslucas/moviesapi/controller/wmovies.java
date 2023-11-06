package br.com.mdroslucas.moviesapi.controller;


import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wmovies")
public class wmovies {

    @Value("${api.url}")
    private String uri;

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/now")
    @ResponseStatus(HttpStatus.OK)
//    public String dadosMovice () throws IOException, InterruptedException {
    public List<DadosMovieTMDB> dadosMovice () throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/discover/movie?include_adult=true&language=pt-br"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("authorization", apiKey)
                .header("accept", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        //        ObjectMapper objectMapper = new ObjectMapper();
//        List<DadosMovieTMDB> dadosMoviesList = objectMapper.readValue(response.body(), new TypeReference<List<DadosMovieTMDB>>() {
//        });

        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        JsonArray resultsArray = jsonObject.get("results").getAsJsonArray();

        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();

        for (JsonElement movieElement : resultsArray) {
            JsonObject movieObject = movieElement.getAsJsonObject();
            DadosMovieTMDB dadosMovie = new DadosMovieTMDB(
                    movieObject.get("title").getAsString(), movieObject.get("overview").getAsString(),
                    movieObject.get("release_date").getAsString(), movieObject.get("backdrop_path").getAsString(),
                    movieObject.get("vote_average").getAsDouble()
            );
            dadosMoviesList.add(dadosMovie);
        }




//        System.out.println(response.body());

//        JsonNode jsonNode = new ObjectMapper().readTree(response.body()).get("results");
//        List<DadosMovieTMDB> dadosMoviesList = new ArrayList<>();
//        jsonNode.elements().forEachRemaining(movie -> {
//            dadosMoviesList.add(new DadosMovieTMDB(
//                    movie.get("title").toString(), movie.get("overview").toString(),
//                    movie.get("release_date").toString(), movie.get("backdrop_path").toString(),
//                    movie.get("vote_average").asDouble()));
//        });
//
//        dadosMoviesList.stream().forEach(movie -> System.out.println(movie.title()));
//        System.out.println(response.body().toString());

        System.out.println(dadosMoviesList);

//        return response.body();
        return dadosMoviesList;
    }
}
