package com.xmies.Library.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class ReviewTest {

    @Autowired
    private Validator validator;

    private Set<ConstraintViolation<Review>> violations;

    private Review review;

    @BeforeEach
    public void beforeEach() {
        review = new Review();
        review.setRating(10);
        review.setComment("Test Comment");
    }

    @AfterEach
    public void afterEach() {
        if (violations != null) {
            violations.clear();
        }
    }

    @Test
    public void createCorrectBook() {
        violations = validator.validate(review);

        assertTrue(violations.isEmpty());
    }

    @Test
    void ratingShouldBeBetween0And10() {
        review.setRating(-1);
        violations = validator.validate(review);

        assertTrue(violations.size() == 1);

        review.setRating(11);
        violations = validator.validate(review);

        assertTrue(violations.size() == 1);
    }

    @Test
    void commentShouldNotBeNull() {
        review.setComment(null);
        violations = validator.validate(review);

        assertTrue(violations.size() == 1);
    }

    @Test
    void commentShouldBeBetween1And512() {
        review.setComment("");
        violations = validator.validate(review);

        assertTrue(violations.size() == 1);

        String tooLongString = "A".repeat(513);
        review.setComment(tooLongString);
        violations = validator.validate(review);

        assertTrue(violations.size() == 1);
    }

}
