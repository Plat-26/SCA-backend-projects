package com.loladebadmus.simplecrudapp.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public Movie addMovie(@Valid @NotNull @RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(path = "{id}")
    public Movie getMovieById(@PathVariable("id") @Valid @NotBlank Long id) {
        return movieService.getMovieById(id);
    }

    @PutMapping(path = "{id}")
    public void updateMovie(@PathVariable Long id,
                            @Valid @NotNull @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
    }

    @DeleteMapping(path = "{id}")
    public void deleteMovie(@PathVariable @Valid @NotBlank Long id) {
        movieService.deleteMovie(id);
    }

}
