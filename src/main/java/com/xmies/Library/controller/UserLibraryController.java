package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.entity.userRelated.User;
import com.xmies.Library.service.LibraryService;
import com.xmies.Library.service.userRelated.UserService;
import com.xmies.Library.user.LibraryUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/library")
public class UserLibraryController {

    private LibraryService libraryService;

    private UserService userService;

    @Autowired
    public UserLibraryController(LibraryService libraryService, UserService userService) {
        this.libraryService = libraryService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/menu")
    public String menu(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null) {
            return "library/menu";
        }

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

            User user = userService.findByUserName(username);

        if (user != null) {
            LibraryUser libraryUser = new LibraryUser(user);
            session.setAttribute("user", libraryUser);
        }


        return "library/menu";
    }

    @GetMapping("/list")
    public String listBooks(Model model) {

        List<Book> books = libraryService.findAllBooks();

        model.addAttribute("books", books);

        return "library/list-books";
    }

    @GetMapping("/authorsList")
    public String getAuthorsList(Model model) {

        List<Author> authors = libraryService.findAllAuthors();

        model.addAttribute("authors", authors);

        return "library/list-authors";
    }

    @GetMapping("/book-information")
    public String bookInformation(@RequestParam("bookId") int id, Model model) {

        Book book = libraryService.findBookAndAuthorsByBookId(id);
        List<Review> reviews = libraryService.findReviewsByBookId(id);

        book.setReviews(reviews);

        model.addAttribute("book", book);
        model.addAttribute("authors", book.getAuthors());


        return "library/book-info";
    }

    @GetMapping("/seeAuthorDetails")
    public String seeAuthorDetails(@RequestParam("authorId") int id, Model model) {

        Author author = libraryService.findAuthorAndAuthorDetailById(id);

        model.addAttribute("author", author);

        return "library/author-details";
    }

    @GetMapping("/addReviewForm")
    public String addReviewForm(@RequestParam("bookId") int id, Model model) {

        Review review = new Review();
        review.setBook(libraryService.findBookById(id));
        model.addAttribute("review", review);

        return "library/review-add-form";
    }

    @PostMapping("/saveReview")
    public String saveReview(@Valid @ModelAttribute("review") Review review,
                             BindingResult bindingResult,
                             @RequestParam int id) {

        if (review.getRating() == 0) {
            System.out.println("dziala");
        }

        if (bindingResult.hasErrors()) {
            return "library/review-add-form";
        }

        libraryService.save(review);

        return "redirect:/library/book-information?bookId=" + review.getBook().getId();
    }
}
