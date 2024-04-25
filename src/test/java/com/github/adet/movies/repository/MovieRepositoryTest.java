package com.github.adet.movies.repository;

import com.github.adet.movies.model.entity.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
class MovieRepositoryTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static final DriverManagerDataSource dataSource;

    MovieRepository movieRepository;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:5.7");
        MY_SQL_CONTAINER.withInitScript("sql/scripts.sql");
        MY_SQL_CONTAINER.withDatabaseName("moviedb");
        MY_SQL_CONTAINER.start();

        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(MY_SQL_CONTAINER.getJdbcUrl());
        dataSource.setUsername(MY_SQL_CONTAINER.getUsername());
        dataSource.setPassword(MY_SQL_CONTAINER.getPassword());
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",() -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username",() -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password",() -> MY_SQL_CONTAINER.getPassword());
    }

    @BeforeEach
    public void beforeEach() {
        movieRepository = new MovieRepositoryImpl(JdbcClient.create(dataSource));
        Movie movie = new Movie();
        movie.setTitle("Mengejar Mimpi");
        movie.setDescription("Berkisah tentang orang lanjut usia yang masih mengejar mimpi.");
        movieRepository.insert(movie);
    }

    @AfterEach
    public void afterEach() {
        List<Movie> all = movieRepository.getAll();
        for (Movie movie : all) {
            if ("Mengejar Mimpi".equals(movie.getTitle())) {
                movieRepository.delete(movie.getId());
            }
        }
    }

    @Test
    void testGetAll() {

        List<Movie> all = movieRepository.getAll();

        for (Movie movie : all) {
            System.out.println(movie.getTitle());
        }

        Assertions.assertEquals(4, all.size());
    }

    @Test
    void testGetById() {
        Optional<Movie> optionalMovie = movieRepository.getById(4);

        Assertions.assertTrue(optionalMovie.isPresent());

        Assertions.assertEquals("Mengejar Mimpi", optionalMovie.get().getTitle());

        optionalMovie = movieRepository.getById(0);

        Assertions.assertTrue(optionalMovie.isEmpty());
    }

    @Test
    void testInsert() {

        Movie movie = new Movie();
        movie.setTitle("Goal 2");
        movie.setDescription("Pemuda inggris yang mempunyai mimpi menjuarai WorldCup bersama negaranya.");

        int insert = movieRepository.insert(movie);

        Assertions.assertTrue(insert > 0);

        Movie movie2 = new Movie();
        movie2.setTitle("Avenger Civil War");

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> movieRepository.insert(movie2));
    }

    @Test
    void testUpdate() {

        Optional<Movie> optionalMovie = movieRepository.getById(3);

        Assertions.assertTrue(optionalMovie.isPresent());

        Movie movie = optionalMovie.get();
        movie.setDescription(movie.getDescription() + " " + "edited");

        int insert = movieRepository.update(movie);

        Assertions.assertTrue(insert > 0);

        movie.setDescription(null);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> movieRepository.update(movie));
    }

    @Test
    void testDelete() {

        Movie movie = new Movie();
        movie.setTitle("Test Title");
        movie.setDescription("Test Description");

        int id = movieRepository.insert(movie);

        int rowsAffected = movieRepository.delete(id);

        Assertions.assertTrue(rowsAffected > 0);

        rowsAffected = movieRepository.delete(33);

        Assertions.assertEquals(0, rowsAffected);
    }
}
