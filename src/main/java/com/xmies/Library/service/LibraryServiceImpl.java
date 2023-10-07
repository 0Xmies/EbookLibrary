package com.xmies.Library.service;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.repository.AuthorDetailsRepository;
import com.xmies.Library.repository.AuthorRepository;
import com.xmies.Library.repository.BookRepository;
import com.xmies.Library.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private AuthorRepository authorRepository;
    private AuthorDetailsRepository authorDetailsRepository;
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public LibraryServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository,
                              ReviewRepository reviewRepository, AuthorDetailsRepository authorDetailsRepository) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.authorDetailsRepository = authorDetailsRepository;
    }

    @Override
    public AuthorDetails findAuthorDetails(int id) {
        Optional<AuthorDetails> result = authorDetailsRepository.findById(id);
        AuthorDetails authorDetails;

        if (result.isPresent()) {
            authorDetails = result.get();
        } else {
            throw new RuntimeException("Did not find authorDetails id = " + id);
        }

        return authorDetails;
    }

    @Override
    public void save(AuthorDetails authorDetails) {
        authorDetailsRepository.save(authorDetails);
    }

    @Override
    public void deleteAuthorDetailsById(int id) {
        authorDetailsRepository.deleteById(id);
    }

    @Override
    public Author findAuthorAndAuthorDetailById(int id) {
        Author author = this.findAuthorById(id);

        if (author.getAuthorDetails() == null) {
            author.setAuthorDetails(this.getBlankAuthorDetails());
        }
        return author;
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<Author> findAuthorsAndBookByBookId(int id) {
        return authorRepository.findAuthorsAndBookByBookId(id);
    }

    @Override
    public Book findBookAndAuthorsByBookId(int id) {
        return bookRepository.findBookAndAuthorsByBookId(id);
    }


    @Override
    public Author findAuthorById(int id) {
        Optional<Author> result = authorRepository.findById(id);
        Author author;

        if (result.isPresent()) {
            author = result.get();
        } else {
            throw new RuntimeException("Did not find author id = " + id);
        }

        return author;
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(int id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public Book findBookById(int id) {
        Optional<Book> result = bookRepository.findById(id);
        Book book;

        if (result.isPresent()) {
            book = result.get();
        } else {
            throw new RuntimeException("Did not find book id = " + id);
        }

        return book;
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAllByOrderByRatingDesc();
    }

    @Override
    public Review findReviewById(int id) {
        Optional<Review> result = reviewRepository.findById(id);
        Review review;

        if (result.isPresent()) {
            review = result.get();
        } else {
            throw new RuntimeException("Did not find review if = " + id);
        }

        return review;
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void deleteReviewById(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void bindAuthorToBook(int authorId, int bookId) {
        Author author = this.findAuthorById(authorId);
        Book book = findBookById(bookId);

        if (isAlreadyAnAuthor(author, book)) {
            return;
        }

        author.addBook(book);
        authorRepository.save(author);
    }

    @Override
    public Book findBookAndReviewsByBookId(int id) {
        Book book = bookRepository.findBookAndReviewsByBookId(id);

        return book;
    }

    @Override
    public List<Review> findReviewsByBookId(int id) {
        return reviewRepository.findReviewsByBookId(id);
    }

    private AuthorDetails getBlankAuthorDetails() {
        return new AuthorDetails("Not known", "Not known", 0);
    }

    private boolean isAlreadyAnAuthor(Author author, Book book) {
        for (Book b : author.getBooks()) {
            if (b == book) {
                return true;
            }
        }

        return false;
    }
}
