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
public class AuthorDetailsTest {

    @Autowired
    private Validator validator;

    private Set<ConstraintViolation<AuthorDetails>> violations;

    private AuthorDetails authorDetails;

    @BeforeEach
    public void beforeEach() {
        authorDetails = new AuthorDetails("Poland", "Football", 1994);
    }

    @AfterEach
    public void afterEach() {
        if (violations != null) {
            violations.clear();
        }
    }

    @Test
    public void createCorrectAuthorDetails() {
        violations = validator.validate(authorDetails);

        assertTrue(violations.isEmpty(), "Should pass since it's correct authorDetails");
    }

    @Test
    public void countryShouldNotBeNull() {
        authorDetails.setCountryOfOrigin(null);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);
    }

    @Test
    public void countryNameShouldBeBetween3And128() {
        authorDetails.setCountryOfOrigin("AB");
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);

        String tooLongString = "A".repeat(129);
        authorDetails.setCountryOfOrigin(tooLongString);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);
    }

    @Test
    public void hobbyShouldNotBeNull() {
        authorDetails.setCountryOfOrigin(null);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);
    }

    @Test
    public void hobbyNameShouldBeBetween3And64() {
        authorDetails.setHobby("AB");
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);

        String tooLongString = "A".repeat(65);
        authorDetails.setHobby(tooLongString);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);
    }

    @Test
    public void birthYearShouldBeBetween0And3000Value() {
        authorDetails.setYearOfBirth(-1);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);

        authorDetails.setYearOfBirth(3001);
        violations = validator.validate(authorDetails);

        assertTrue(violations.size() == 1);
    }

}
