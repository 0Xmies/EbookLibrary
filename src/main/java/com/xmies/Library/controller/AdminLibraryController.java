package com.xmies.Library.controller;

import com.xmies.Library.entity.Book;
import com.xmies.Library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book) {

        libraryService.save(book);

        return "redirect:/library/list";
    }

    @GetMapping("/updateBookForm")
    public String updateBook(@RequestParam("bookId") int id, Model model) {

        Book book = libraryService.findBookById(id);

        System.out.println(book);
        model.addAttribute("book", book);

        return "library/book-add-form";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("bookId") int id) {

        //Book book = libraryService.findBookById(id);

        libraryService.deleteBookById(id);

        return "redirect:/library/list";
    }
}
