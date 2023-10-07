package com.xmies.Library.repository;

import com.xmies.Library.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    List<Review> findAllByOrderByRatingDesc();

    @Query("SELECT r FROM Review r " +
            "JOIN r.book b " +
            "WHERE b.id = ?1")
    List<Review> findReviewsByBookId(int id);
}
