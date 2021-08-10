package com.loladebadmus.simplecrudapp.movies;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MovieControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void addMovie() {
        Movie movie = new Movie("Cinderella", "Disney", "Fairytale", true, 300.0);

        ResponseEntity<Movie> response = restTemplate.postForEntity("http://localhost:" + port + "/movies", movie, Movie.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getAllMovies() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:" + port + "/movies", List.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getMovieById() {
        ResponseEntity<Movie> response = restTemplate.getForEntity("http://localhost:" + port + "/movies/1", Movie.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void updateMovie() {
    }

    @Test
    void deleteMovie() {
    }
}