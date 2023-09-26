package com.xmies.Library.repository.userRelated;

import com.xmies.Library.entity.userRelated.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u " +
            "WHERE u.username = ?1")
    User findByUserName(String username);
}
