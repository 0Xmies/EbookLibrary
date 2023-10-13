package com.xmies.Library.service.userRelated;

import com.xmies.Library.entity.userRelated.Role;
import com.xmies.Library.entity.userRelated.Users;
import com.xmies.Library.repository.userRelated.RoleRepository;
import com.xmies.Library.repository.userRelated.UserRepository;
import com.xmies.Library.user.LibraryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void save(LibraryUser libraryUser) {
        Users users = new Users();

        users.setUsername(libraryUser.getUsername());
        users.setPassword(passwordEncoder.encode(libraryUser.getPassword()));
        users.setEnabled(true);
        users.setFirstName(libraryUser.getFirstName());
        users.setLastName(libraryUser.getLastName());

        users.addRole(roleRepository.findRoleByName("ROLE_USER"));

        userRepository.save(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(username);

        if (users == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        List<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(users.getRoles());

        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), authorities);
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(authority);
        }

        return authorities;
    }
}
