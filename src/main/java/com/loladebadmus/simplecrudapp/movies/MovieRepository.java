package com.loladebadmus.simplecrudapp.movies;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM movies WHERE m.title = ?1")
    Optional<Movie> getMovieByTitle(String Title);
}
