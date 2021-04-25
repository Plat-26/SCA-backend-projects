package com.loladebadmus.simplecrudapp.movies.api;

import com.loladebadmus.simplecrudapp.movies.model.Movie;
import com.loladebadmus.simplecrudapp.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/movies")
@RestController
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(path = "{id}")
    public Optional<Movie> getMovieById(@PathVariable("id") UUID id) {
        return movieService.getMovieById(id);
    }

    @PutMapping(path = "{id}")
    public void updateMovie(@PathVariable UUID id, @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
    }

    @DeleteMapping(path = "{id}")
    public void deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
    }
}
