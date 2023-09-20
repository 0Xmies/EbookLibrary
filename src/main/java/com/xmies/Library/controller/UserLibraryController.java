package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.Book;
import com.xmies.Library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        Book book = libraryService.findBookById(id);
        model.addAttribute("book", book);

        return "library/book-info";
    }
}