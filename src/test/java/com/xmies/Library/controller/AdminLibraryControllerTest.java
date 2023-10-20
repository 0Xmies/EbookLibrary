package com.xmies.Library.controller;


import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.AuthorDetails;
import com.xmies.Library.entity.Book;
import com.xmies.Library.service.LibraryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class AdminLibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${script.sql.create-books}")
    private String sqlCreateBooks;

    @Value("${script.sql.delete-books}")
    private String sqlDeleteBooks;

    @Value("${script.sql.create-authors}")
    private String sqlCreateAuthors;

    @Value("${script.sql.delete-authors}")
    private String sqlDeleteAuthors;

    @Value("${script.sql.create-authors-details}")
    private String sqlCreateAuthorDetails;

    @Value("${script.sql.delete-authors-details}")
    private String sqlDeleteAuthorDetails;

    @Value("${script.sql.delete-reviews}")
    private String sqlDeleteReviews;

    @Value("${script.sql.create-book_author}")
    private String sqlCreateBook_AuthorTable;

    @Value("${script.sql.delete-book_author}")
    private String sqlDeleteBook_AuthorTable;

    @Value("${script.sql.create-review}")
    private String sqlCreateReview;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlCreateBooks);
        jdbc.execute(sqlCreateReview);
        jdbc.execute(sqlCreateAuthorDetails);
        jdbc.execute(sqlCreateAuthors);
        jdbc.execute(sqlCreateBook_AuthorTable);
    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteBooks);
        jdbc.execute(sqlDeleteReviews);
        jdbc.execute(sqlDeleteAuthors);
        jdbc.execute(sqlDeleteAuthorDetails);
        jdbc.execute(sqlDeleteBook_AuthorTable);
    }

    @Test
    public void tryWithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                .with(user("fakeUser").roles("USER")))
                .andExpect(status().is(403));
    }

    @Test
    public void getAddBookForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/addBookForm")
                .with(user("fakeAdmin").roles("USER", "ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/book-add-form");
    }

    @Test
    public void postSaveBook() throws Exception {
        Book book = new Book("Book title");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/saveBook")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("book", book)
                .with(user("fakeAdmin").roles("USER", "ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/list");
        assertSame(book.getTitle(), libraryService.findBookById(6).getTitle());
    }

    @Test
    public void putUpdateBookForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateBookForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Book book = libraryService.findBookById(1);

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "book", book);
        ModelAndViewAssert.assertViewName(modelAndView, "library/book-add-form");
    }

    @Test
    public void putUpdateBookFormInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateBookForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "6"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteBook")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/list");
        assertFalse(libraryService.bookExistsById(1));
    }

    @Test
    public void deleteBookInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteBook")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER","ADMIN"))
                .param("bookId", "6"))
                .andExpect(status().is(404));

        assertFalse(libraryService.bookExistsById(6));
    }

    @Test
    public void getAddAuthorForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/addAuthorForm")
                .with(user("fakeAdmin").roles("USER", "ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/author-add-form");
    }

    @Test
    public void postSaveAuthor() throws Exception {
        Author author = new Author("First Name Check", "Last Name Check");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/saveAuthor")
                .with(csrf())
                .flashAttr("author", author)
                .with(user("fakeAdmin").roles("USER", "ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/authorsList");
        assertSame(author.getLastName(), libraryService.findAuthorById(6).getLastName());
    }

    @Test
    public void postSaveAuthorWithoutAdminRole() throws Exception {
        Author author = new Author("First Name Check", "Last Name Check");

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/saveAuthor")
                .with(csrf())
                .flashAttr("author", author)
                .with(user("notAdmin").roles("USER")))
                .andExpect(status().is(403));
    }

    @Test
    public void putUpdateAuthorForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateAuthorForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Author author = libraryService.findAuthorById(1);

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "author", author);
        ModelAndViewAssert.assertViewName(modelAndView, "library/author-add-form");
    }

    @Test
    public void putUpdateAuthorFormInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateAuthorForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "6"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteAuthor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteAuthor")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/authorsList");

        assertFalse(libraryService.authorExistsById(1));
    }

    @Test
    public void deleteAuthorInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteAuthor")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "6"))
                .andExpect(status().is(404));

        assertFalse(libraryService.authorExistsById(6));
    }

    @Test
    public void getManageAuthors() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/manageAuthors")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/list-authors-for-book");
    }

    @Test
    public void getManageAuthorsInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/manageAuthors")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "6"))
                .andExpect(status().is(404));
    }

    @Test
    public void getBindAuthorToBookList () throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/bindAuthorToBookList")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/bind-author-to-book");
    }

    @Test
    public void getBindAuthorToBookListInvalidId () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/bindAuthorToBookList")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "6"))
                .andExpect(status().is(404));
    }

    @Test
    public void putBindAuthorToBook() throws Exception {
        Book book = libraryService.findBookAndAuthorsByBookId(2);
        assertTrue(book.getAuthors().size() == 1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/bindAuthorToBook")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1")
                .param("bookId", "2"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        book = libraryService.findBookAndAuthorsByBookId(2);
        assertTrue(book.getAuthors().size() == 2);
        assertTrue(book.getAuthors().get(0).equals(libraryService.findAuthorById(2)));
        assertTrue(book.getAuthors().get(1).equals(libraryService.findAuthorById(1)));

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/menu");
    }

    @Test
    public void getManageAuthorDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/manageAuthorDetails")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Author author = libraryService.findAuthorById(1);

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/author-details-manager");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "author", author);
    }

    @Test
    public void getManageAuthorDetailsInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/manageAuthorDetails")
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "6"))
                .andExpect(status().is(404));

        assertFalse(libraryService.authorExistsById(6));
    }

    @Test
    public void putUpdateAuthorDetails() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateAuthorDetails")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Author author = libraryService.findAuthorAndAuthorDetailById(1);

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "library/author-details-add-form");
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "authorDetails", author.getAuthorDetails());
    }

    @Test
    public void putUpdateAuthorDetailsInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/updateAuthorDetails")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("authorId", "6"))
                .andExpect(status().is(404));
    }

    @Test
    public void saveAuthorDetails() throws Exception {
        AuthorDetails details = new AuthorDetails("testCountry", "testHobby", 1010);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/saveAuthorDetails")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .flashAttr("authorDetails", details)
                .param("authorId", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/seeAuthorDetails?authorId=1");

        Author author = libraryService.findAuthorById(1);
        assertEquals(author.getAuthorDetails().getCountryOfOrigin(), details.getCountryOfOrigin());
        assertEquals(author.getAuthorDetails().getHobby(), details.getHobby());
        assertEquals(author.getAuthorDetails().getYearOfBirth(), details.getYearOfBirth());
    }

    @Test
    public void putUpdateReview() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/admin/reviewUpdateForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1")
                .param("reviewId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "library/review-add-form");
    }

    @Test
    public void putUpdateReviewInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/reviewUpdateForm")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1")
                .param("reviewId", "2"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteReview() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteReview")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1")
                .param("reviewId", "1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/library/book-information?bookId=1");
        assertFalse(libraryService.reviewExistsById(1));
    }

    @Test
    public void deleteReviewInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/deleteReview")
                .with(csrf())
                .with(user("fakeAdmin").roles("USER", "ADMIN"))
                .param("bookId", "1")
                .param("reviewId", "2"))
                .andExpect(status().is(404));
    }
}