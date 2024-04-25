package com.github.adet.movies.controller;

import com.github.adet.movies.exception.BadRequestException;
import com.github.adet.movies.model.request.MovieRequest;
import com.github.adet.movies.model.response.MovieResponse;
import com.github.adet.movies.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> movies = movieService.getAll();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest movieRequest) {

        int id = movieService.add(movieRequest);

        MovieResponse response = movieService.getById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable("movieId") int movieId) {

        if (movieId <= 0) {
            throw new BadRequestException();
        }

        MovieResponse movie = movieService.getById(movieId);
        return ResponseEntity.ok(movie);
    }

    @PatchMapping("/{movieId}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable("movieId") int movieId, @Valid @RequestBody MovieRequest movieRequest) {

        movieService.update(movieId, movieRequest);

        MovieResponse response = movieService.getById(movieId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable("movieId") int movieId) {

        if (movieId <= 0) {
            throw new BadRequestException();
        }

        movieService.delete(movieId);

        return ResponseEntity.noContent().build();
    }
}
