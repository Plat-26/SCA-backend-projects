package com.loladebadmus.simplecrudapp.movies;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "Movie")
@Table(name = "movies",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_movie_title", columnNames = "title")
})
public class Movie {
    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "movie_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @NotBlank(message = "Please enter the movie title")
    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    private String title;

    @NotBlank(message = "Please enter the movie producer name")
    @Column(
            name = "producer",
            nullable = false,
            columnDefinition = "VARCHAR(25)"
    )
    private String producer;

    @NotBlank(message = "Please give a movie description")
    @Column(
            name = "description",
            columnDefinition = "VARCHAR(50)"
    )
    private String desc;

    @NotNull
    @Column(
            name = "available",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean isAvailable;

    @NotNull
    @Column(precision = 2)
    private Double price;

    public Movie(
                 @JsonProperty("title") String title,
                 @JsonProperty("producer") String producer,
                 @JsonProperty("description") String desc,
                 @JsonProperty("available") boolean isAvailable,
                 @JsonProperty("price") Double price) {
        this.title = title;
        this.producer = producer;
        this.desc = desc;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public Movie() {

    }

    public String getProducer() {
        return producer;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", producer='" + producer + '\'' +
                ", desc='" + desc + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
