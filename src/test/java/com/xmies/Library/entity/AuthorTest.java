package com.xmies.Library.entity;


import com.xmies.Library.entity.userRelated.Role;
import com.xmies.Library.entity.userRelated.Users;
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
        if (violations != null) {
            violations.clear();
        }
    }

    @Test
    public void createCorrectAuthor() {
        violations = validator.validate(author);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void addBookWhenNull () {
        assertNull(author.getBooks());

        Book book = new Book("TEST BOOK");
        author.addBook(book);

        assertTrue(author.getBooks().size() == 1);
        assertSame(book, author.getBooks().get(0));
    }

    @Test
    public void addBookWhenOtherBooksPresent () {
        author.setBooks(new ArrayList<>(Arrays.asList(new Book(), new Book(), new Book())));

        Book book = new Book("TEST BOOK");
        author.addBook(book);

        assertTrue(author.getBooks().size() == 4);
        assertSame(book, author.getBooks().get(3));
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
