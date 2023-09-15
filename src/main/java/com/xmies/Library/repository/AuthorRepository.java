package com.xmies.Library.repository;

import com.xmies.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAllByOrderByLastNameAsc();
}
