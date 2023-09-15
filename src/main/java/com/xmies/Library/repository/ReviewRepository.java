package com.xmies.Library.repository;

import com.xmies.Library.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findAllByOrderByRatingDesc();
}
