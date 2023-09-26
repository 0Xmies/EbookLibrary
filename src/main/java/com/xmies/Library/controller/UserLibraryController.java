package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/library")
public class UserLibraryController {

    private LibraryService libraryService;

    @Autowired
    public UserLibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/menu")
    public String menu() {

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
    public String seeAuthorDetails(@RequestParam("bookId") int bookId, @RequestParam("authorId") int id, Model model) {

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
    public String saveReview(@ModelAttribute("review") Review review, @RequestParam int id) {

        libraryService.save(review);

        return "redirect:/library/book-information?bookId=" + review.getBook().getId();
    }
}
