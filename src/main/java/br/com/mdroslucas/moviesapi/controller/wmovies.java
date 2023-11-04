package br.com.mdroslucas.moviesapi.controller;


import br.com.mdroslucas.moviesapi.model.movie.DadosMovieTMDB;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public void dadosMovice () throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "/discover/movie?include_adult=true&language=pt-br"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("authorization", apiKey)
                .header("accept", "application/json")
                .build();

//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        Gson gson = new Gson();
//        DadosMovieTMDB dados = gson.fromJson(response.body(), DadosMovieTMDB.class);
//        System.out.println(dados);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
//        DadosMovieTMDB dados = dadosArray.stream();
//        DadosMovieTMDB[] dadosArray = new ObjectMapper().read(response.body(), DadosMovieTMDB[].class);

//        JsonNode rootNode =  new ObjectMapper().readTree(response.body()).get("results");
        JsonNode jsonNode = new ObjectMapper().readTree(response.body()).get("results");

        jsonNode.

        if(jsonNode.isArray()) {
            for (JsonNode movies: jsonNode){
               var teste =  new DadosMovieTMDB(movies.get("title").toString());
                System.out.println(teste);
            }
        }



    }
}
