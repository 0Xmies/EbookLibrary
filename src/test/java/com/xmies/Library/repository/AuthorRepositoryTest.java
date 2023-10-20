package com.xmies.Library.repository;

import com.xmies.Library.entity.Author;
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
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

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

    @Value("${script.sql.create-book_author}")
    private String sqlCreateBook_AuthorTable;

    @Value("${script.sql.delete-book_author}")
    private String sqlDeleteBook_AuthorTable;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateBooks);
        jdbc.execute(sqlCreateAuthorDetails);
        jdbc.execute(sqlCreateAuthors);
        jdbc.execute(sqlCreateBook_AuthorTable);
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteBooks);
        jdbc.execute(sqlDeleteAuthors);
        jdbc.execute(sqlDeleteAuthorDetails);
        jdbc.execute(sqlDeleteBook_AuthorTable);
    }

    @Test
    public void findAllAuthorsAndOrderByLastNameAsc() {
        List<Author> authors = authorRepository.findAllByOrderByLastNameAsc();
        List<Author> sortedAuthors = (List<Author>) authorRepository.findAll();

        Comparator<Author> comparator = Comparator.comparing(Author::getLastName);
        Collections.sort(sortedAuthors, comparator);

        assertIterableEquals(sortedAuthors, authors);
    }

    @Test
    public void findAuthorsAndBookByBookId() {
        List<Author> authors = authorRepository.findAuthorsAndBookByBookId(1);
        assertTrue(authors.get(0).getBooks().size() == 1);
    }


}
