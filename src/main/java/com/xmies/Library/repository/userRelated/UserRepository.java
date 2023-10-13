package com.xmies.Library.repository.userRelated;

import com.xmies.Library.entity.userRelated.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Integer> {

    @Query("SELECT u FROM Users u " +
            "WHERE u.username = ?1")
    Users findByUserName(String username);
}
