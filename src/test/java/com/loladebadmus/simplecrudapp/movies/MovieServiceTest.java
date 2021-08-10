package com.loladebadmus.simplecrudapp.movies;

import com.loladebadmus.simplecrudapp.rentals.RentalRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    MovieService movieService = new MovieService();

    private static Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie(
                "Cinderella",
                "Disney",
                "Fairytale",
                true,
                300.0
        );
    }



    @Test
    void addMovie_happy_path() {
        Movie savedMovie = movieService.addMovie(MovieServiceTest.movie);

        verify(movieRepository).getMovieByTitle(MovieServiceTest.movie.getTitle());
        verify(movieRepository.save(MovieServiceTest.movie));
        assertThat(savedMovie).isNotNull();
    }

    @Test
    void getAllMovies_happy_path() {
        when(movieRepository.findAll()).thenReturn(Lists.newArrayList(movie));

        List<Movie> allMovies = movieService.getAllMovies();
        verify(movieRepository).findAll();
        assertThat(allMovies).contains(movie);
    }

    @Test
    void getMovieById_happy_path() {
        Long id = 0L;
        when(movieRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(movie));

        Movie expectedMovie = movieService.getMovieById(id);
        verify(movieRepository.findById(0L));
        assertThat(expectedMovie).isNotNull();

    }

    @Test
    void getMovieByTitle_happy_path() {
        when(movieRepository.getMovieByTitle(movie.getTitle())).thenReturn(java.util.Optional.ofNullable(movie));

        Movie expectedMovie = movieService.getMovieByTitle(movie.getTitle());
        verify(movieRepository).getMovieByTitle(movie.getTitle());
        assertThat(expectedMovie).isNotNull();
    }

    @Test
    void updateMovie_happy_path() {
        Long id = 0L;
        Movie movieUpdate = new Movie(
                "Clouds",
                "Disney",
                "Tragic love",
                true,
                350.0
        );
        when(movieRepository.findById(id)).thenReturn(Optional.ofNullable(movie));

        Movie updatedMovie = movieService.updateMovie(id, movieUpdate);
        verify(movieRepository.findById(id));
        Movie subUpdate = verify(movieService.updateMovie(MovieServiceTest.movie, movieUpdate));
        verify(movieRepository).save(subUpdate);

    }

    @Test
    void deleteMovie_happy_path() {
        Long id = 0L;
        when(movieRepository.existsById(id)).thenReturn(true);

        movieService.deleteMovie(id);
        verify(rentalRepository.findByMovieId(id));
        verify(movieRepository).deleteById(0L);
    }
}