package com.github.adet.movies.service;

import com.github.adet.movies.exception.DataNotFoundException;
import com.github.adet.movies.model.entity.Movie;
import com.github.adet.movies.model.request.MovieRequest;
import com.github.adet.movies.model.response.MovieResponse;
import com.github.adet.movies.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Test
    void testGetAllMovies() {

        Movie movie1 = new Movie();
        movie1.setTitle("Mencuri Raden Saleh");
        movie1.setDescription("Film mencuri barang seni yang bernilai fantastis.");
        movie1.setRating(7.7);

        Movie movie2 = new Movie();
        movie2.setTitle("13 Bom Jakarta");
        movie2.setDescription("Sebuah film tentang penjahat yang ingin menghancurkan Jakarta.");
        movie2.setImage("https://img.service.io/BomJakarta.jpg");

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.getAll()).thenReturn(movies);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        List<MovieResponse> responses = movieService.getAll();

        Assertions.assertEquals(2, responses.size());
        Assertions.assertEquals(movie1.getTitle(), responses.get(0).getTitle());
        Assertions.assertEquals(movie2.getTitle(), responses.get(1).getTitle());
    }

    @Test
    void testGetAllMovies_NoResult() {

        List<Movie> movies = new ArrayList<>();

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.getAll()).thenReturn(movies);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        List<MovieResponse> responses = movieService.getAll();

        Assertions.assertEquals(0, responses.size());
    }

    @Test
    void testGetById() {

        Movie movie = new Movie();
        movie.setTitle("Mencuri Raden Saleh");
        movie.setDescription("Film mencuri barang seni yang bernilai fantastis.");
        movie.setRating(7.7);

        Optional<Movie> optionalMovie = Optional.of(movie);

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.getById(1)).thenReturn(optionalMovie);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        MovieResponse response = movieService.getById(1);

        Assertions.assertEquals(movie.getTitle(), response.getTitle());
    }

    @Test
    void testGetById_NoResult() {

        Optional<Movie> optionalMovie = Optional.empty();

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.getById(1)).thenReturn(optionalMovie);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        Assertions.assertThrows(DataNotFoundException.class, () -> movieService.getById(1));
    }

    @Test
    void testAddMovie() {

        MovieRequest request = new MovieRequest();
        request.setTitle("Membelah Lautan Hindia");
        request.setDescription("Cerita tentang pemuda dengan halusinasi luar biasa.");
        request.setRating(5.3);

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.insert(Mockito.any(Movie.class))).thenReturn(1);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        int id = movieService.add(request);

        Assertions.assertEquals(1, id);
    }

    @Test
    void testUpdateMovie() {

        MovieRequest request = new MovieRequest();
        request.setTitle("Membelah Lautan Hindia");
        request.setDescription("Cerita tentang pemuda dengan halusinasi luar biasa.");
        request.setRating(5.3);

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.update(Mockito.any(Movie.class))).thenReturn(1);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        Assertions.assertDoesNotThrow(() -> movieService.update(1, request));
    }

    @Test
    void testUpdateMovie_NoResult() {

        MovieRequest request = new MovieRequest();
        request.setTitle("Membelah Lautan Hindia");
        request.setDescription("Cerita tentang pemuda dengan halusinasi luar biasa.");
        request.setRating(5.3);

        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.update(Mockito.any(Movie.class))).thenReturn(0);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        Assertions.assertThrows(DataNotFoundException.class, () -> movieService.update(1, request));
    }

    @Test
    void testDeleteMovie() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.delete(1)).thenReturn(1);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        Assertions.assertDoesNotThrow(() -> movieService.delete(1));
    }

    @Test
    void testDeleteMovie_NoResult() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Mockito.when(movieRepository.delete(1)).thenReturn(0);

        MovieService movieService = new MovieServiceImpl(movieRepository);

        Assertions.assertThrows(DataNotFoundException.class, () -> movieService.delete(1));
    }
}
