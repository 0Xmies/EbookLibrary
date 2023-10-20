package com.xmies.Library.repository;


import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${script.sql.create-books}")
    private String sqlCreateBooks;

    @Value("${script.sql.delete-books}")
    private String sqlDeleteBooks;

    @Value("${script.sql.create-authors}")
    private String sqlCreateAuthors;

    @Value("${script.sql.delete-authors}")
    private String sqlDeleteAuthors;

    @Value("${script.sql.create-authors-details}")
    private String sqlCreateAuthorDetails;

    @Value("${script.sql.delete-authors-details}")
    private String sqlDeleteAuthorDetails;

    @Value("${script.sql.delete-reviews}")
    private String sqlDeleteReviews;

    @Value("${script.sql.delete-statistics}")
    private String sqlDeleteStatistics;

    @Value("${script.sql.create-book_author}")
    private String sqlCreateBook_AuthorTable;

    @Value("${script.sql.delete-book_author}")
    private String sqlDeleteBook_AuthorTable;

    @Value("${script.sql.create-review}")
    private String sqlCreateReview;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateBooks);
        jdbc.execute(sqlCreateAuthorDetails);
        jdbc.execute(sqlCreateReview);
        jdbc.execute("INSERT IGNORE INTO review (id, rating, comment, book_id) values (2, 3, 'bad', 1)");
        jdbc.execute("INSERT IGNORE INTO review (id, rating, comment, book_id) values (3, 5, 'medium', 1)");
        jdbc.execute("INSERT IGNORE INTO review (id, rating, comment, book_id) values (4, 10, 'perfect', 1)");
        jdbc.execute(sqlCreateAuthors);
        jdbc.execute(sqlCreateBook_AuthorTable);
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteBooks);
        jdbc.execute(sqlDeleteReviews);
        jdbc.execute(sqlDeleteAuthors);
        jdbc.execute(sqlDeleteAuthorDetails);
        jdbc.execute(sqlDeleteBook_AuthorTable);
    }

    @Test
    public void findAllReviewsAndOrderByRatingDesc() {
        List<Review> authors = reviewRepository.findAllByOrderByRatingDesc();
        List<Review> sortedReviews = (List<Review>) reviewRepository.findAll();

        Comparator<Review> comparator = (r1, r2) -> r2.getRating() - r1.getRating();
        Collections.sort(sortedReviews, comparator);

        assertIterableEquals(sortedReviews, authors);
    }

    @Test
    public void findAllReviewsByBookId() {
        List<Review> reviews = reviewRepository.findReviewsByBookId(1);
        assertTrue(reviews.size() == 4);
    }
}
