package com.xmies.Library.user;

import com.xmies.Library.entity.userRelated.Users;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LibraryUser {

    @NotNull(message = "is required")
    @Size(min = 2, max = 50, message = "between 2 and 50 characters")
    private String username;

    @NotNull(message = "is required")
    @Size(min = 3, max = 50, message = "between 3 and 50 characters")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50, message = "is required")
    private String lastName;

    public LibraryUser() {
    }

    public LibraryUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LibraryUser(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LibraryUser(Users users) {
        this.username = users.getUsername();
        this.password = users.getPassword();
        this.firstName = users.getFirstName();
        this.lastName = users.getLastName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
