package com.loladebadmus.simplecrudapp.movies;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT m FROM Movies m WHERE m.title = ?1", nativeQuery = true)
    Optional<Movie> getMovieByTitle(String Title);
}
