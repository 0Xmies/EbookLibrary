package com.xmies.Library.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class BookTest {

    @Autowired
    private Validator validator;

    private Set<ConstraintViolation<Book>> violations;

    private Book book;

    @BeforeEach
    public void beforeEach() {
        book = new Book("Test title");
    }

    @AfterEach
    public void afterEach() {
        if (violations != null) {
            violations.clear();
        }
    }

    @Test
    public void createCorrectBook() {
        violations = validator.validate(book);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void addAuthorWhenNull () {
        assertNull(book.getAuthors());

        Author author = new Author("TEST AUTHOR NAME", "TEST AUTHOR LASTNAME");
        book.addAuthor(author);

        assertTrue(book.getAuthors().size() == 1);
        assertSame(author, book.getAuthors().get(0));
    }

    @Test
    public void addAuthorWhenOtherAuthorsPresent () {
        book.setAuthors(new ArrayList<>(Arrays.asList(new Author(), new Author(), new Author())));

        Author author = new Author("TEST AUTHOR NAME", "TEST AUTHOR LASTNAME");
        book.addAuthor(author);

        assertTrue(book.getAuthors().size() == 4);
        assertSame(author, book.getAuthors().get(3));
    }

    @Test
    public void addReviewWhenNull () {
        assertNull(book.getReviews());

        Review review = new Review();
        book.addReview(review);

        assertTrue(book.getReviews().size() == 1);
        assertSame(review, book.getReviews().get(0));
    }

    @Test
    public void addReviewWhenOtherReviewsPresent () {
        book.setReviews(new ArrayList<>(Arrays.asList(new Review(), new Review(), new Review())));

        Review review = new Review();
        book.addReview(review);

        assertTrue(book.getReviews().size() == 4);
        assertSame(review, book.getReviews().get(3));
    }

    @Test
    void titleShouldNotBeNull() {
        book.setTitle(null);
        violations = validator.validate(book);

        assertTrue(violations.size() == 1);
    }

    @Test
    void firstNameShouldBeBetween1And512() {
        book.setTitle("");
        violations = validator.validate(book);

        assertTrue(violations.size() == 1);

        String tooLongString = "A".repeat(513);
        book.setTitle(tooLongString);
        violations = validator.validate(book);

        assertTrue(violations.size() == 1);
    }
}
