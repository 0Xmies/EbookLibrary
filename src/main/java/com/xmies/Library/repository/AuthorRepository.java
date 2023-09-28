package com.xmies.Library.repository;

import com.xmies.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAllByOrderByLastNameAsc();

    @Query("SELECT a FROM Author a " +
            "JOIN FETCH a.books b " +
            "WHERE b.id = ?1")
    List<Author> findAuthorsAndBookByBookId(int id);
}
