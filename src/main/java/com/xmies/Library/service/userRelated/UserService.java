package com.xmies.Library.service.userRelated;

import com.xmies.Library.entity.userRelated.Users;
import com.xmies.Library.user.LibraryUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users findByUserName(String userName);

    void save(LibraryUser libraryUser);
}
