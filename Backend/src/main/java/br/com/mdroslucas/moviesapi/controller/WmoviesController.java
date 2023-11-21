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
        return httpService.getMovieById(id);
    }



    @GetMapping("/search/{movieName}")
    public List<DadosMovieTMDB> searchMovieByName(@PathVariable String movieName) throws IOException, InterruptedException {
        return httpService.searchMovieByName(movieName);
    }

    @GetMapping("/popular")
    public List<DadosMovieTMDB> getPopularMovise () throws IOException, InterruptedException {
        return httpService.getPopularMovies();
    }
}
