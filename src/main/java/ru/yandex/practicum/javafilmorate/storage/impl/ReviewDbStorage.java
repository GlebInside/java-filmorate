package ru.yandex.practicum.javafilmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Review;
import ru.yandex.practicum.javafilmorate.storage.ReviewStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ReviewDbStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;


    public ReviewDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addReview(Review review) {
        String sqlQuery = "INSERT INTO Reviews VALUES(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                review.getReviewId(),
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful());
    }

    @Override
    public void updateReview(Review review) {
        String sqlQuery = "UPDATE Reviews SET " +
                "content = ?," +
                "is_positive = ?," +
                "user_id = ?," +
                "film_id = ?," +
                "useful = ? " +
                "WHERE review_id = ?";

        jdbcTemplate.update(sqlQuery,
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUserId(),
                review.getReviewId());
    }

    @Override
    public void deleteReview(Long id) {
        String sqlQuery = "DELETE FROM Reviews WHERE review_id = ?";

        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Review getReviewById(Long id) {
        String sqlQuery = "SELECT * FROM Reviews WHERE review_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::reviewFromSQL, id);
    }

    @Override
    public List<Review> getReviewsOfFilm(Long filmId) {
        String sqlQuery = "SELECT * FROM Reviews WHERE film_id = ?";

        return jdbcTemplate.query(sqlQuery, this::reviewFromSQL, filmId);
    }

    @Override
    public void putLike(Long reviewId, Long userId) {
        String sqlQuery = "INSERT INTO Reviews_likes VALUES (?, ?, true)";

        jdbcTemplate.update(sqlQuery, reviewId, userId);
    }

    @Override
    public void putDislike(Long reviewId, Long userId) {
        String sqlQuery = "INSERT INTO Reviews_likes VALUES (?, ?, false)";

        jdbcTemplate.update(sqlQuery, reviewId, userId);
    }

    @Override
    public void deleteLike(Long reviewId, Long userId) {
        String sqlQuery = "DELETE FROM Reviews_likes WHERE review_id = ? AND user_id = ? AND reaction = true";

        jdbcTemplate.update(sqlQuery, reviewId, userId);
    }

    @Override
    public void deleteDislike(Long reviewId, Long userId) {
        String sqlQuery = "DELETE FROM Reviews_likes WHERE review_id = ? AND user_id = ? AND reaction = false";

        jdbcTemplate.update(sqlQuery, reviewId, userId);
    }

    private Review reviewFromSQL(ResultSet rs, Integer rowNum) throws SQLException {
        Long reviewId = rs.getLong("review_id");
        String content = rs.getString("content");
        Boolean isPositive = rs.getBoolean("is_positive");
        Long userId = rs.getLong("user_id");
        Long filmId = rs.getLong("film_id");
        Integer useful = rs.getInt("useful");

        return new Review(reviewId, content, isPositive, userId, filmId, useful);
    }
}