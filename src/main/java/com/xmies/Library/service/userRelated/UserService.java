package com.xmies.Library.service.userRelated;

import com.xmies.Library.entity.userRelated.User;
import com.xmies.Library.user.LibraryUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(LibraryUser libraryUser);
}
