package com.xmies.Library.controller;

import com.xmies.Library.entity.Author;
import com.xmies.Library.entity.Book;
import com.xmies.Library.entity.Review;
import com.xmies.Library.entity.Statistics;
import com.xmies.Library.service.LibraryService;
import com.xmies.Library.service.StatisticsService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class UserLibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private StatisticsService statisticsService;

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

    @Value("${script.sql.delete-statistics}")
    private String sqlDeleteStatistics;

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
    public void tryInvalidURL() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/abc123abc123"))
                .andExpect(status().is(404));
    }

    @Test
    public void getMenuView() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"))
                .andExpect(status().isOk())
                .andReturn();

        Statistics statistics = statisticsService.getStatistics();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "statistics", statistics);
        ModelAndViewAssert.assertViewName(modelAndView, "library/menu");
    }

    @Test
    public void getBookListView() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/list"))
                .andExpect(status().isOk())
                .andReturn();

        List<Book> books = libraryService.findAllBooks();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "books", books);
        ModelAndViewAssert.assertViewName(modelAndView, "library/list-books");
    }

    @Test
    public void getAuthorsListView() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/authorsList"))
                .andExpect(status().isOk())
                .andReturn();

        List<Author> authors = libraryService.findAllAuthors();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "authors", authors);
        ModelAndViewAssert.assertViewName(modelAndView, "library/list-authors");
    }

    @Test
    public void getBookInformationView() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/book-information")
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Book book = libraryService.findBookAndAuthorsByBookId(1);
        List<Author> authors = book.getAuthors();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        Iterable<Author> authorsFromModel = (Iterable<Author>) modelAndView.getModel().get("authors");

        assertIterableEquals(authorsFromModel, authors);
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "book", book);
        ModelAndViewAssert.assertViewName(modelAndView, "library/book-info");
    }

    @Test
    public void getBookInformationViewErrorWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/book-information")
                .param("bookId", "0"))
                .andExpect(status().is(404));
    }

    @Test
    public void getBookInformationViewErrorWrongFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/book-information")
                .param("bookId", "abc"))
                .andExpect(status().is(400));
    }

    @Test
    public void getAuthorDetailsView() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/seeAuthorDetails")
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        Author author = libraryService.findAuthorById(1);

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "author", author);
        ModelAndViewAssert.assertViewName(modelAndView, "library/author-details");
    }

    @Test
    public void getAuthorDetailsViewWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/seeAuthorDetails")
                .param("authorId", "0"))
                .andExpect(status().is(404));
    }

    @Test
    public void getReviewAddForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/library/addReviewForm")
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/review-add-form");
    }

    @Test
    public void getReviewAddFormWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/addReviewForm")
                .param("bookId", "0"))
                .andExpect(status().is(404));
    }

    @Test
    public void postSaveReview() throws Exception {
        Book book = libraryService.findBookById(1);
        Review review = new Review();
        review.setRating(3);
        review.setComment("not good");
        review.setBook(book);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/library/saveReview")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .flashAttr("review", review))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView,
                "redirect:/library/book-information?bookId=" + book.getId());
        assertTrue(libraryService.reviewExistsById(2));
        assertEquals(review.getComment() ,libraryService.findReviewById(2).getComment());
    }
}