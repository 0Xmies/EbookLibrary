package com.xmies.Library.controller;

import com.xmies.Library.user.LibraryUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entry")
public class EntryController {

    @GetMapping("showLoginPage")
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
}
