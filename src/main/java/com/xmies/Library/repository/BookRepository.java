package com.xmies.Library.repository;

import com.xmies.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByTitleAsc();
}
