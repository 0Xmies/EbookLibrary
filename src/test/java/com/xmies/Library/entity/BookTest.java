package com.xmies.Library.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        violations.clear();
    }

    @Test
    public void createCorrectBook() {
        violations = validator.validate(book);

        assertTrue(violations.isEmpty());
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
