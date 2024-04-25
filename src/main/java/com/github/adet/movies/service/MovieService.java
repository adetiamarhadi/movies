package com.github.adet.movies.service;

import com.github.adet.movies.model.request.MovieRequest;
import com.github.adet.movies.model.response.MovieResponse;

import java.util.List;

public interface MovieService {

    List<MovieResponse> getAll();
    MovieResponse getById(int movieId);
    int add(MovieRequest request);
    void update(int movieId, MovieRequest request);
    void delete(int movieId);
}
