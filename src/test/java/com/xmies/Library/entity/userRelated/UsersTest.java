package com.xmies.Library.entity.userRelated;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UsersTest {

    private Users users;

    @BeforeEach
    public void beforeEach() {
        users = new Users();
    }

    @Test
    public void addRoleWhenNull () {
        assertNull(users.getRoles());

        Role role = new Role("ROLE_NEWROLE");
        users.addRole(role);

        assertTrue(users.getRoles().size() == 1);
        assertSame(role, users.getRoles().get(0));
    }

    @Test
    public void addRoleWhenOtherRolesPresent () {
        users.setRoles(new ArrayList<>(Arrays.asList(new Role(), new Role(), new Role())));

        Role role = new Role("ROLE_NEWROLE");
        users.addRole(role);

        assertTrue(users.getRoles().size() == 4);
        assertSame(role, users.getRoles().get(3));
    }
}
