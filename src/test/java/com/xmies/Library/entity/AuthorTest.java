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
public class AuthorTest {
    @Autowired
    private Validator validator;

    private Set<ConstraintViolation<Author>> violations;

    private Author author;

    @BeforeEach
    public void beforeEach() {
        author = new Author("Test name", "Test last name");
    }

    @AfterEach
    public void afterEach() {
        violations.clear();
    }

    @Test
    public void createCorrectAuthor() {
        violations = validator.validate(author);

        assertTrue(violations.isEmpty());
    }

    @Test
    void firstNameShouldNotBeNull() {
        author.setFirstName(null);
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of NotNull constraint");
    }

    @Test
    void firstNameShouldBeBetween3And128() {
        author.setFirstName("AB");
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of Size 3-128 constraint");

        String tooLongString = "A".repeat(129);
        author.setFirstName(tooLongString);
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of Size 3-128 constraint");
    }

    @Test
    void lastNameShouldNotBeNull() {
        author.setFirstName(null);
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of NotNull constraint");
    }

    @Test
    void lastNameShouldBeBetween3And128() {
        author.setLastName("AB");
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of Size 3-128 constraint");

        String tooLongString = "A".repeat(129);
        author.setLastName(tooLongString);
        violations = validator.validate(author);

        assertTrue(violations.size() == 1, "Should pass cause of Size 3-128 constraint");
    }
}
