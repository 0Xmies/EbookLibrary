package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminLibraryController {

    private LibraryService libraryService;

    public AdminLibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/addBookForm")
    public String addBookForm(Model model) {

        Book book = new Book();
        model.addAttribute("book", book);

        return "library/book-add-form";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {

        libraryService.save(book);

        return "redirect:/library/list";
    }

    @GetMapping("/updateBookForm")
    public String updateBook(@RequestParam("bookId") int id, Model model) {

        Book book = libraryService.findBookById(id);
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
    public String saveAuthor(@ModelAttribute("author") Author author) {

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
        model.addAttribute("author", author);

        return "library/author-details-add-form";
    }

    @PostMapping("saveAuthorDetails")
    public String saveAuthorDetails(@ModelAttribute("author") Author author) {

        libraryService.save(author);

        return "redirect:/library/seeAuthorDetails?authorId=" + author.getId();
    }

}












