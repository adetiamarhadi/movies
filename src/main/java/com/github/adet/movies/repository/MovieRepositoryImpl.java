package com.github.adet.movies.repository;

import com.github.adet.movies.model.entity.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private final JdbcClient jdbcClient;

    public MovieRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Movie> getAll() {
        return jdbcClient.sql("SELECT id, title, description, rating, image, UNIX_TIMESTAMP(created_at) as created_at, UNIX_TIMESTAMP(updated_at) as updated_at FROM movies ORDER BY id")
                .query(Movie.class)
                .list();
    }

    @Override
    public Optional<Movie> getById(int movieId) {
        return jdbcClient.sql("SELECT id, title, description, rating, image, UNIX_TIMESTAMP(created_at) as created_at, UNIX_TIMESTAMP(updated_at) as updated_at FROM movies WHERE id = :movieId")
                .param("movieId", movieId, Types.INTEGER)
                .query(Movie.class)
                .optional();
    }

    @Override
    public int insert(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into movies (title, description, rating, image) values (:title, :description, :rating, :image)")
                .param("title", movie.getTitle(), Types.VARCHAR)
                .param("description", movie.getDescription(), Types.VARCHAR)
                .param("image", movie.getImage(), Types.VARCHAR)
                .param("rating", movie.getRating(), Types.DECIMAL)
                .update(keyHolder);
        BigInteger key = keyHolder.getKeyAs(BigInteger.class);

        return key.intValue();
    }

    @Override
    public int update(Movie movie) {
        return jdbcClient.sql("UPDATE movies SET title = :title, description = :description, rating = :rating, image = :image WHERE id = :id")
                .param("title", movie.getTitle(), Types.VARCHAR)
                .param("description", movie.getDescription(), Types.VARCHAR)
                .param("image", movie.getImage(), Types.VARCHAR)
                .param("rating", movie.getRating(), Types.DECIMAL)
                .param("id", movie.getId(), Types.INTEGER)
                .update();
    }

    @Override
    public int delete(int movieId) {
        return jdbcClient.sql("DELETE FROM movies WHERE id = :id")
                .param("id", movieId, Types.INTEGER)
                .update();
    }
}
