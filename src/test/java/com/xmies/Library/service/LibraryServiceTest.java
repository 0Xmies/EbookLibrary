package com.xmies.Library.service;

import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.repository.AuthorDetailsRepository;
import com.xmies.Library.repository.AuthorRepository;
import com.xmies.Library.repository.BookRepository;
import com.xmies.Library.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class LibraryServiceTest {

    @Autowired
    private LibraryService libraryService;

    @MockBean
    private AuthorRepository authorRepositoryMock;

    @MockBean
    private AuthorDetailsRepository authorDetailsRepositoryMock;

    @MockBean
    private BookRepository bookRepositoryMock;

    @MockBean
    private ReviewRepository reviewRepositoryMock;

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

    @Value("${script.sql.create-book_author}")
    private String sqlCreateBook_AuthorTable;

    @Value("${script.sql.delete-book_author}")
    private String sqlDeleteBook_AuthorTable;

    @Value("${script.sql.create-review}")
    private String sqlCreateReview;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateBooks);
        jdbc.execute(sqlCreateReview);
        jdbc.execute(sqlCreateAuthorDetails);
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
    public void bookExistsAndNotExists() {
        when(bookRepositoryMock.existsById(1)).thenReturn(true);
        when(bookRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryService.bookExistsById(1));
        assertFalse(libraryService.bookExistsById(0));
    }

    @Test
    public void authorExistsAndNotExists() {
        when(authorRepositoryMock.existsById(1)).thenReturn(true);
        when(authorRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryService.authorExistsById(1));
        assertFalse(libraryService.authorExistsById(0));
    }

    @Test
    public void authorDetailsExistsAndNotExists() {
        when(authorDetailsRepositoryMock.existsById(1)).thenReturn(true);
        when(authorDetailsRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryService.authorDetailsExistsById(1));
        assertFalse(libraryService.authorExistsById(0));
    }

    @Test
    public void reviewExistsAndNotExists() {
        when(reviewRepositoryMock.existsById(1)).thenReturn(true);
        when(reviewRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryService.reviewExistsById(1));
        assertFalse(libraryService.reviewExistsById(0));
    }

    @Test
    public void findAuthorDetailsByIdWhenExists() {
        AuthorDetails authorDetails = new AuthorDetails("A", "B", 3);

        when(authorDetailsRepositoryMock.findById(1)).thenReturn(Optional.of(authorDetails));
        AuthorDetails returnedAuthorDetails = libraryService.findAuthorDetailsById(1);

        assertEquals(authorDetails, returnedAuthorDetails);
    }
}
