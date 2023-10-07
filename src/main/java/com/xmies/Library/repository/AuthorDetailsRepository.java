package com.xmies.Library.repository;

import com.xmies.Library.entity.AuthorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AuthorDetailsRepository extends CrudRepository<AuthorDetails, Integer> {
}
