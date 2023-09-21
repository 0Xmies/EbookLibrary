package com.xmies.Library.service;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetail;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;

import java.util.List;

public interface LibraryService {

    AuthorDetail findAuthorDetail(int id);

    void save(AuthorDetail authorDetail);

    void deleteAuthorDetailById(int id);

    List<Author> findAllAuthors();

    List<Author> findAuthorsAndBookByBookId(int id);

    Book findBookAndAuthorsByBookId(int id);

    Author findAuthorById(int id);

    void save(Author author);

    void deleteAuthorById(int id);

    List<Book> findAllBooks();

    Book findBookById(int id);

    void save(Book book);

    void deleteBookById(int id);

    List<Review> findAllReviews();

    Review findReviewById(int id);

    void save(Review review);

    void deleteReviewById(int id);

    void bindAuthorToBook(int authorId, int bookId);

}
