package com.xmies.Library.repository;

import com.xmies.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByTitleAsc();

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.authors a " +
            "WHERE b.id = ?1")
    Book findBookAndAuthorsByBookId(int id);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.reviews r " +
            "where b.id = ?1")
    Book findBookAndReviewsByBookId(int id);
}
