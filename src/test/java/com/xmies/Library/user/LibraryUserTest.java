package com.xmies.Library.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(value = DisplayNameGenerator.Simple.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest
public class LibraryUserTest {

    @Autowired
    private Validator validator;

    private Set<ConstraintViolation<LibraryUser>> violations;

    private LibraryUser libraryUser;

    @Value("${library-user.fake-user.username}")
    private String Username;

    @Value("${library-user.fake-user.password}")
    private String password;

    @Value("${library-user.fake-user.firstname}")
    private String firstName;

    @Value("${library-user.fake-user.lastname}")
    private String lastName;


    @BeforeEach
    public void beforeEach() {
        libraryUser = new LibraryUser(Username, password, firstName, lastName);
    }

    @AfterEach
    public void afterEach() {
        violations.clear();
    }

    @Test
    void createCorrectLibraryUser() {
        violations = validator.validate(libraryUser);

        assertTrue(violations.isEmpty(), "Should pass as this is correct Library Users");
    }

    @Test
    void usernameShouldNotBeNull() {
        libraryUser.setUsername(null);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of NotNull constraint");
    }

    @Test
    void usernameShouldBeBetween2And50() {
        libraryUser.setUsername("A");
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 2-50 constraint");

        String tooLongString = "A".repeat(51);
        libraryUser.setUsername(tooLongString);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 2-50 constraint");
    }

    @Test
    void passwordShouldNotBeNullBeforeHashing() {
        libraryUser.setPassword(null);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of NotNull constraint");
    }

    @Test
    void passwordShouldBeBetween3And50BeforeHashing() {
        libraryUser.setPassword("AB");
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 3-50 constraint");

        String tooLongString = "*".repeat(51);
        libraryUser.setPassword(tooLongString);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 3-50 constraint");
    }

    @Test
    void firstNameShouldNotBeNull() {
        libraryUser.setFirstName(null);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of NotNull constraint");
    }

    @Test
    void firstNameShouldBeBetween1And50() {
        libraryUser.setFirstName("");
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 1-50 constraint");

        String tooLongString = "A".repeat(51);
        libraryUser.setFirstName(tooLongString);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 1-50 constraint");
    }

    @Test
    void lastNameShouldNotBeNull() {
        libraryUser.setLastName(null);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of NotNull constraint");
    }

    @Test
    void lastNameShouldBeBetween1And50() {
        libraryUser.setLastName("");
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 1-50 constraint");

        String tooLongString = "A".repeat(51);
        libraryUser.setLastName(tooLongString);
        violations = validator.validate(libraryUser);

        assertTrue(violations.size() == 1, "Should fail cause of Size 1-50 constraint");
    }
}
