package com.xmies.Library.repository.userRelated;

import com.xmies.Library.entity.userRelated.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u " +
            "WHERE u.username = ?1")
    User findByUserName(String username);
}
