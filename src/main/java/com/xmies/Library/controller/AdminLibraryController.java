package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PutMapping("/updateBookForm")
    public String updateBook(@RequestParam("bookId") int id, Model model) {

        if (!libraryService.bookExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
        }

        Book book = libraryService.findBookById(id);

        model.addAttribute("book", book);

        return "library/book-add-form";
    }

    @DeleteMapping("/deleteBook")
    public String deleteBook(@RequestParam("bookId") int id) {

        if (!libraryService.bookExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
        }

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

    @PutMapping("/updateAuthorForm")
    public String updateAuthor(@RequestParam("authorId") int id, Model model) {

        if (!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }

        Author author = libraryService.findAuthorById(id);

        model.addAttribute("author", author);

        return "library/author-add-form";
    }

    @DeleteMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam("authorId") int id) {

        if(!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }

        libraryService.deleteAuthorById(id);

        return "redirect:/library/authorsList";
    }

    @GetMapping("/manageAuthors")
    public String listAuthors(@RequestParam("bookId") int id, Model model) {

        if (!libraryService.bookExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book");
        }

        List<Author> authors = libraryService.findAuthorsAndBookByBookId(id);
        model.addAttribute("authors", authors);

        return "library/list-authors-for-book";
    }

    @GetMapping("bindAuthorToBookList")
    public String bindAuthorToBookList(@RequestParam("authorId") int id, Model model) {

        if (!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }
        List<Book> books = libraryService.findAllBooks();
        Author author = libraryService.findAuthorById(id);

        model.addAttribute("books", books);
        model.addAttribute("author", author);

        return "library/bind-author-to-book";
    }

    @PutMapping("bindAuthorToBook")
    public String bindAuthorToBook(@RequestParam("authorId") int authorId, @RequestParam("bookId") int bookId) {

        if (!libraryService.bookExistsById(bookId) || !libraryService.authorExistsById(authorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find given author and/or book");
        }

        libraryService.bindAuthorToBook(authorId, bookId);

        return "redirect:/library/menu";
    }

    @GetMapping("manageAuthorDetails")
    public String manageAuthorDetails(@RequestParam("authorId") int id, Model model) {

        if (!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }

        Author author = libraryService.findAuthorById(id);
        model.addAttribute("author", author);

        return "library/author-details-manager";
    }

    @PutMapping("updateAuthorDetails")
    public String updateAuthorDetails(@RequestParam("authorId") int id, Model model) {

        if (!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }

        Author author = libraryService.findAuthorAndAuthorDetailById(id);
        model.addAttribute("authorDetails", author.getAuthorDetails());

        return "library/author-details-add-form";
    }

    @PostMapping("saveAuthorDetails")
    public String saveAuthorDetails(@RequestParam("authorId") int id,
                                    @Valid @ModelAttribute("authorDetails") AuthorDetails authorDetails,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "library/author-details-add-form";
        }

        if (!libraryService.authorExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find author");
        }

        Author author = libraryService.findAuthorById(id);
        author.setAuthorDetails(authorDetails);

        libraryService.save(author);

        return "redirect:/library/seeAuthorDetails?authorId=" + id;
    }

    @PutMapping("reviewUpdateForm")
    public String reviewUpdateForm(@RequestParam("bookId") int bookId,
                                   @RequestParam("reviewId") int reviewId,
                                   Model model) {

        if (!libraryService.bookExistsById(bookId) || !libraryService.reviewExistsById(reviewId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find book and/or review");
        }

        Review review = libraryService.findReviewById(reviewId);

        model.addAttribute("review", review);

        return "library/review-add-form";
    }

    @DeleteMapping("deleteReview")
    public String deleteReview(@RequestParam("bookId") int bookId, @RequestParam("reviewId") int id) {

        if (!libraryService.reviewExistsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find review");
        }

        libraryService.deleteReviewById(id);

        return "redirect:/library/book-information?bookId=" + bookId;
    }
}












