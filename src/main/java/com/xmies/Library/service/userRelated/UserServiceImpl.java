package com.xmies.Library.service.userRelated;

import com.xmies.Library.entity.userRelated.Role;
import com.xmies.Library.entity.userRelated.User;
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

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void save(LibraryUser libraryUser) {

        User user = new User();

        user.setUsername(libraryUser.getUsername());
        user.setPassword(libraryUser.getPassword());
        user.setFirstName(libraryUser.getFirstName());
        user.setLastName(libraryUser.getLastName());

        user.addRole(new Role("ROLE_USER"));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        List<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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
