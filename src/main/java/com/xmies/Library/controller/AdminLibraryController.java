package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminLibraryController {

    private LibraryService libraryService;

    public AdminLibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/addBookForm")
    public String addBookForm(Model model) {

        Book book = new Book();
        model.addAttribute("book", book);

        return "library/book-add-form";
    }

    @PostMapping("/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "library/book-add-form";
        }

        libraryService.save(book);

        return "redirect:/library/list";
    }

    @GetMapping("/updateBookForm")
    public String updateBook(@RequestParam("bookId") int id, Model model) {

        Book book = libraryService.findBookAndAuthorsByBookId(id);
        model.addAttribute("book", book);

        return "library/book-add-form";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("bookId") int id) {

        libraryService.deleteBookById(id);

        return "redirect:/library/list";
    }

    @GetMapping("/addAuthorForm")
    public String addAuthorForm(Model model) {

        Author author = new Author();
        model.addAttribute("author", author);

        return "library/author-add-form";
    }

    @PostMapping("/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "library/author-add-form";
        }

        libraryService.save(author);

        return "redirect:/library/authorsList";
    }

    @GetMapping("/updateAuthorForm")
    public String updateAuthor(@RequestParam("authorId") int id, Model model) {

        Author author = libraryService.findAuthorById(id);
        model.addAttribute("author", author);

        return "library/author-add-form";
    }

    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam("authorId") int id) {

        libraryService.deleteAuthorById(id);

        return "redirect:/library/authorsList";
    }

    @GetMapping("/manageAuthors")
    public String listAuthors(@RequestParam("bookId") int id, Model model) {

        List<Author> authors = libraryService.findAuthorsAndBookByBookId(id);
        model.addAttribute("authors", authors);

        return "library/list-authors-for-book";
    }

    @GetMapping("bindAuthorToBookList")
    public String bindAuthorToBookList(@RequestParam("authorId") int id, Model model) {

        List<Book> books = libraryService.findAllBooks();
        Author author = libraryService.findAuthorById(id);

        model.addAttribute("books", books);
        model.addAttribute("author", author);

        return "library/bind-author-to-book";
    }

    @GetMapping("bindAuthorToBook")
    public String bindAuthorToBook(@RequestParam("authorId") int id, @RequestParam("bookId") int bookId) {

        libraryService.bindAuthorToBook(id, bookId);

        return "redirect:/library/menu";
    }

    @GetMapping("manageAuthorDetails")
    public String manageAuthorDetails(@RequestParam("authorId") int id, Model model) {

        Author author = libraryService.findAuthorById(id);
        model.addAttribute("author", author);

        return "library/author-details-manager";
    }

    @GetMapping("updateAuthorDetails")
    public String updateAuthorDetails(@RequestParam("authorId") int id, Model model) {

        Author author = libraryService.findAuthorById(id);
        AuthorDetails authorDetails = author.getAuthorDetails();

        model.addAttribute("authorDetails", authorDetails);

        return "library/author-details-add-form";
    }

    @PostMapping("saveAuthorDetails")
    public String saveAuthorDetails(@RequestParam("authorId") int id,
                                    @Valid @ModelAttribute("authorDetails") AuthorDetails authorDetails,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "library/author-details-add-form";
        }

        libraryService.save(authorDetails);

        return "redirect:/library/seeAuthorDetails?authorId=" + id;
    }

    @GetMapping("reviewUpdateForm")
    public String reviewUpdateForm(@RequestParam("bookId") int bookId, @RequestParam("reviewId") int id, Model model) {

        Review review = libraryService.findReviewById(id);

        model.addAttribute("review", review);

        return "library/review-add-form";
    }

    @GetMapping("deleteReview")
    public String deleteReview(@RequestParam("bookId") int bookId, @RequestParam("reviewId") int id) {

        libraryService.deleteReviewById(id);

        return "redirect:/library/book-information?bookId=" + bookId;
    }

}












