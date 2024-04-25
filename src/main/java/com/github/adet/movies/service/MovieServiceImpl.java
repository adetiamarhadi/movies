package com.github.adet.movies.service;

import com.github.adet.movies.exception.DataNotFoundException;
import com.github.adet.movies.model.entity.Movie;
import com.github.adet.movies.model.request.MovieRequest;
import com.github.adet.movies.model.response.MovieResponse;
import com.github.adet.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieResponse> getAll() {

        List<Movie> movies = movieRepository.getAll();
        if (movies.isEmpty()) {
            return Collections.emptyList();
        }

        return movies.stream()
                .map(movie -> new MovieResponse(movie.getId(), movie.getTitle(), movie.getDescription(),
                        movie.getRating(), movie.getImage(), movie.getCreatedAt(), movie.getUpdatedAt()))
                .toList();
    }

    @Override
    public MovieResponse getById(int movieId) {

        Optional<Movie> movieOptional = movieRepository.getById(movieId);
        if (movieOptional.isEmpty()) {
            throw new DataNotFoundException();
        }

        Movie movie = movieOptional.get();

        return new MovieResponse(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getRating(), movie.getImage(), movie.getCreatedAt(), movie.getUpdatedAt());
    }

    @Override
    public int add(MovieRequest request) {

        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setRating(request.getRating());
        movie.setImage(request.getImage());

        return movieRepository.insert(movie);
    }

    @Override
    public void update(int movieId, MovieRequest request) {

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setRating(request.getRating());
        movie.setImage(request.getImage());

        int rowsAffected = movieRepository.update(movie);
        if (rowsAffected == 0) {
            throw new DataNotFoundException();
        }
    }

    @Override
    public void delete(int movieId) {

        int rowsAffected = movieRepository.delete(movieId);
        if (rowsAffected == 0) {
            throw new DataNotFoundException();
        }
    }
}
