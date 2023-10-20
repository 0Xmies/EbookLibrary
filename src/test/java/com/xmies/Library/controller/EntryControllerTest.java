package com.xmies.Library.controller;

import com.xmies.Library.entity.userRelated.Users;
import com.xmies.Library.user.LibraryUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class EntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLoginPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/entry/showLoginPage"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/entry/login-page");
    }

    @Test
    public void getLoginUserForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/entry/showLoginPage"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/entry/login-page");
    }

    @Test
    public void getAccessDenied() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/entry/accessDenied"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/entry/access-denied");
    }

    @Test
    public void getAccessDeniedByAccessingAdminOnly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/top-secret-stuff"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void postProcessRegistrationFormSuccess() throws Exception {
        LibraryUser libraryUser = new LibraryUser(
                "Test username", "testPassword",
                "test first name", "test last name");

        Users users = new Users();
        users.setUsername("Test username");
        users.setFirstName("Test first name");
        users.setLastName("Test last name");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/entry/processRegistrationForm")
                .with(csrf())
                .flashAttr("libraryUser", libraryUser)
                .sessionAttr("users", users))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "library/entry/registration-successful");
    }


}
