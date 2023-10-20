package com.xmies.Library.service;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.repository.AuthorDetailsRepository;
import com.xmies.Library.repository.AuthorRepository;
import com.xmies.Library.repository.BookRepository;
import com.xmies.Library.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class LibraryServiceTest {

    @Autowired
    private LibraryService libraryServiceWithMocks;

    @MockBean
    private AuthorRepository authorRepositoryMock;

    @MockBean
    private AuthorDetailsRepository authorDetailsRepositoryMock;

    @MockBean
    private BookRepository bookRepositoryMock;

    @MockBean
    private ReviewRepository reviewRepositoryMock;


    @Test
    public void getBlankAuthorDetailsWhenNotPresent() {
        Author author = new Author("Author name", "Without details");
        assertNull(author.getAuthorDetails());

        when(authorRepositoryMock.findById(1)).thenReturn(Optional.of(author));

        AuthorDetails defaultAuthorDetails = new AuthorDetails
                ("Not known", "Not known", 0);

        Author authorFromService = libraryServiceWithMocks.findAuthorAndAuthorDetailById(1);
        assertSame(authorFromService.getAuthorDetails().getYearOfBirth(), defaultAuthorDetails.getYearOfBirth());
        assertEquals(authorFromService.getAuthorDetails().getHobby(), defaultAuthorDetails.getHobby());
        assertEquals(authorFromService.getAuthorDetails().getCountryOfOrigin(), defaultAuthorDetails.getCountryOfOrigin());
    }

    @Test
    public void findAuthorDetailsByIdWhenExists() {
        AuthorDetails authorDetails = new AuthorDetails("A", "B", 3);

        when(authorDetailsRepositoryMock.findById(1)).thenReturn(Optional.of(authorDetails));
        AuthorDetails returnedAuthorDetails = libraryServiceWithMocks.findAuthorDetailsById(1);

        assertEquals(authorDetails, returnedAuthorDetails);
    }

    @Test
    public void findAuthorDetailsByIdWhenItDoesNotExistsThrowsException() {
        when(authorDetailsRepositoryMock.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> libraryServiceWithMocks.findAuthorDetailsById(1));
    }

    @Test
    public void saveNullAuthorDetailsThrowsException() {
        AuthorDetails authorDetails = null;

        when(authorDetailsRepositoryMock.save(authorDetails)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,() -> libraryServiceWithMocks.save(authorDetails));
    }

    @Test
    public void deleteAuthorDetailsWithNullIdThrowsException() {
        Integer integer = null;

        assertThrows(NullPointerException.class,() -> libraryServiceWithMocks.deleteAuthorDetailsById(integer));
    }

    @Test
    public void findAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1 name", "1 last name"));
        authors.add(new Author("2 name", "2 last name"));
        authors.add(new Author("3 name", "3 last name"));
        authors.add(new Author("4 name", "4 last name"));

        when(authorRepositoryMock.findAllByOrderByLastNameAsc()).thenReturn(authors);

        assertIterableEquals(authors, libraryServiceWithMocks.findAllAuthors());
    }

    @Test
    public void findAuthorByIdPresent() {
        Author author = new Author("Name", "Last name");
        when(authorRepositoryMock.findById(1)).thenReturn(Optional.of(author));

        Author fromServiceAuthor = libraryServiceWithMocks.findAuthorById(1);

        assertEquals(author.getFirstName(), fromServiceAuthor.getFirstName());
        assertEquals(author.getLastName(), fromServiceAuthor.getLastName());
    }

    @Test
    public void findAuthorByIdEmptyThrowException() {
        when(authorRepositoryMock.findById(1)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,() -> libraryServiceWithMocks.findAuthorById(1));
    }

    @Test
    public void findBookByIdPresent() {
        Book book = new Book("Test book title");
        when(bookRepositoryMock.findById(1)).thenReturn(Optional.of(book));

        Book fromServiceBook = libraryServiceWithMocks.findBookById(1);

        assertEquals(book.getTitle(), fromServiceBook.getTitle());
    }

    @Test
    public void findBookByIdEmptyThrowException() {
        when(bookRepositoryMock.findById(1)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,() -> libraryServiceWithMocks.findBookById(1));
    }

    @Test
    public void findReviewByIdPresent() {
        Book bookForReview = new Book("Review me book");
        Review review = new Review(9, "Great", bookForReview);

        when(reviewRepositoryMock.findById(1)).thenReturn(Optional.of(review));

        Review fromServiceReview = libraryServiceWithMocks.findReviewById(1);

        assertEquals(review.getRating(), fromServiceReview.getRating());
        assertEquals(review.getComment(), fromServiceReview.getComment());
        assertEquals(review.getBook(), fromServiceReview.getBook());
    }

    @Test
    public void findReviewByIdEmptyThrowException() {
        when(reviewRepositoryMock.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> libraryServiceWithMocks.findReviewById(1));
    }

    @Test
    public void bookExistsAndNotExists() {
        when(bookRepositoryMock.existsById(1)).thenReturn(true);
        when(bookRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryServiceWithMocks.bookExistsById(1));
        assertFalse(libraryServiceWithMocks.bookExistsById(0));
    }

    @Test
    public void authorExistsAndNotExists() {
        when(authorRepositoryMock.existsById(1)).thenReturn(true);
        when(authorRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryServiceWithMocks.authorExistsById(1));
        assertFalse(libraryServiceWithMocks.authorExistsById(0));
    }

    @Test
    public void authorDetailsExistsAndNotExists() {
        when(authorDetailsRepositoryMock.existsById(1)).thenReturn(true);
        when(authorDetailsRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryServiceWithMocks.authorDetailsExistsById(1));
        assertFalse(libraryServiceWithMocks.authorExistsById(0));
    }

    @Test
    public void reviewExistsAndNotExists() {
        when(reviewRepositoryMock.existsById(1)).thenReturn(true);
        when(reviewRepositoryMock.existsById(0)).thenReturn(false);

        assertTrue(libraryServiceWithMocks.reviewExistsById(1));
        assertFalse(libraryServiceWithMocks.reviewExistsById(0));
    }
    @Test
    public void bindAuthorToBook() {
        Author author = new Author("Author of \"My Book\"", "Test last name");
        Book book = new Book("My Book");

        assertNull(author.getBooks());
        assertNull(book.getAuthors());

        when(authorRepositoryMock.findById(1)).thenReturn(Optional.of(author));
        when(bookRepositoryMock.findById(1)).thenReturn(Optional.of(book));

        libraryServiceWithMocks.bindAuthorToBook(1, 1);

        assertEquals(author.getBooks().get(0).getTitle(), book.getTitle());
    }

    @Test
    public void bindAuthorToSameBookWorksWhenRepeating() {
        Author author = new Author("Author of \"My Book\"", "Test last name");
        Book book = new Book("My Book");
        author.addBook(book);
        book.addAuthor(author);

        when(authorRepositoryMock.findById(1)).thenReturn(Optional.of(author));
        when(bookRepositoryMock.findById(1)).thenReturn(Optional.of(book));

        libraryServiceWithMocks.bindAuthorToBook(1, 1);

        assertEquals(author.getBooks().get(0).getTitle(), book.getTitle());
    }
}
