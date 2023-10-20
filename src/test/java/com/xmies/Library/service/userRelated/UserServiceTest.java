package com.xmies.Library.service.userRelated;


import com.xmies.Library.entity.userRelated.Users;
import com.xmies.Library.repository.userRelated.RoleRepository;
import com.xmies.Library.repository.userRelated.UserRepository;
import com.xmies.Library.user.LibraryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEach() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0;" +
                " DELETE FROM Users; SET FOREIGN_KEY_CHECKS=1;");
    }

    @Test
    public void saveUserAndCheckIfPasswordIsEncoded() {
        LibraryUser libraryUser = new LibraryUser(
                "Test username", "testPassword",
                "test first name", "test last name");

        userService.save(libraryUser);

        Users users = userRepository.findByUserName("Test username");

        assertEquals(libraryUser.getFirstName(), users.getFirstName());
        assertTrue(users.getPassword().length() == 60);
        assertTrue(passwordEncoder.matches(libraryUser.getPassword(), users.getPassword()));
        assertEquals(libraryUser.getFirstName(), users.getFirstName());
        assertEquals(libraryUser.getFirstName(), users.getFirstName());
    }

    @Test
    public void loadByUserNameAndCheckRoles() {
        LibraryUser libraryUser = new LibraryUser(
                "Test username", "testPassword",
                "test first name", "test last name");
        userService.save(libraryUser);

        assertDoesNotThrow(() -> userService.loadUserByUsername(libraryUser.getUsername()));

        UserDetails userDetails = userService.loadUserByUsername(libraryUser.getUsername());
        assertTrue(passwordEncoder.matches(libraryUser.getPassword(), userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().size() == 1);

        Users user = userRepository.findByUserName(libraryUser.getUsername());
        SimpleGrantedAuthority[] authorities = {new SimpleGrantedAuthority(user.getRoles().get(0).getName())};

        assertArrayEquals(userDetails.getAuthorities().toArray(), authorities);
    }
}
