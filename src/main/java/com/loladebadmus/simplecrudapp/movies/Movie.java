package com.loladebadmus.simplecrudapp.movies;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Movie {

    private final UUID id;
    private final String title;
    private final String producer;
    private final String desc;
    private final boolean isAvailable;

    public Movie(@JsonProperty("id") UUID id,
                 @JsonProperty("title") String title,
                 @JsonProperty("producer") String producer,
                 @JsonProperty("description") String desc,
                 @JsonProperty("available") boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.producer = producer;
        this.desc = desc;
        this.isAvailable = isAvailable;
    }

    public String getProducer() {
        return producer;
    }

    public UUID getId() {
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
}
