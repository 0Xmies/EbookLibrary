package com.xmies.Library.controller;

import com.xmies.Library.entity.userRelated.User;
import com.xmies.Library.service.userRelated.UserService;
import com.xmies.Library.user.LibraryUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/entry")
public class EntryController {

    private UserService userService;

    public EntryController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "library/entry/login-page";
    }

    @GetMapping("/accessDenied")
    public String showAccessDenied() {
        return "library/entry/access-denied";
    }

    @GetMapping("/loginUserForm")
    public String loginUserForm() {
        return "library/entry/login-page";
    }

    @GetMapping("/registerUserForm")
    public String registerUserForm(Model model) {
        LibraryUser libraryUser = new LibraryUser();

        model.addAttribute("libraryUser", libraryUser);

        return "library/entry/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("libraryUser") LibraryUser libraryUser,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        String username = libraryUser.getUsername();

        if (bindingResult.hasErrors()) {
            return "library/entry/registration-form";
        }

        User existing = userService.findByUserName(username);
        if (existing != null) {
            model.addAttribute("libraryUser", new User());
            model.addAttribute("registrationError", "Username taken");

            return "library/entry/registration-form";
        }

        userService.save(libraryUser);

        session.setAttribute("user", libraryUser);

        return "library/entry/registration-successful";
    }
}
