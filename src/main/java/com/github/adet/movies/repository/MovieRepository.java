package com.github.adet.movies.repository;

import com.github.adet.movies.model.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> getAll();
    Optional<Movie> getById(int movieId);
    int insert(Movie movie);
    int update(Movie movie);
    int delete(int movieId);
}
