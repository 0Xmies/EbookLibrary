package com.xmies.Library.service;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;

import java.util.List;

public interface LibraryService {

    List<Author> findAllAuthors();

    Author findAuthorById(int id);

    void save(Author author);

    void deleteAuthorById(int id);

    List<Book> findAllBooks();

    Book findBookById(int id);

    void save(Book book);

    void deleteBookById(int id);

    List<Review> getAllReviews();

    Review findReviewById(int id);

    void save(Review review);

    void deleteReviewById(int id);
}
