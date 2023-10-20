package com.xmies.Library.repository.userRelated;

import com.xmies.Library.entity.userRelated.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("SELECT r FROM Role r " +
            "WHERE r.name = ?1")
    Role findRoleByName(String roleName);
}
