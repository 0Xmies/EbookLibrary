package com.xmies.Library.repository;

import com.xmies.Library.entity.Book;
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
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

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
    public void findAllBooksAndOrderByTitleAsc() {
        List<Book> books = bookRepository.findAllByOrderByTitleAsc();
        List<Book> sortedBooks = (List<Book>) bookRepository.findAll();

        Comparator<Book> comparator = Comparator.comparing(Book::getTitle);
        Collections.sort(sortedBooks, comparator);

        assertIterableEquals(sortedBooks, books);
    }

    @Test
    public void findBookAndAuthorsByBookId() {
        Book books = bookRepository.findBookAndAuthorsByBookId(1);
        assertTrue(books.getAuthors().size() == 1);
    }

    @Test
    public void findBookAndReviewsByBookId() {
        Book book = bookRepository.findBookAndReviewsByBookId(1);
        assertTrue(book.getReviews().size() == 1);
    }
}
