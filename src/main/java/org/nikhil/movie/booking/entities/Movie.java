package org.nikhil.movie.booking.entities;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Movie {
    private String id;
    private String name;

    public Movie(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
