package com.xmies.Library.aspect;

import com.xmies.Library.entity.Statistics;
import com.xmies.Library.service.StatisticsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsAspectTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private StatisticsAspect statisticsAspect;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${script.sql.create-statistics}")
    private String sqlCreateStatistics;

    @Value("${script.sql.delete-statistics}")
    private String sqlDeleteStatistics;

    @BeforeEach
    public void beforeEach() {
        jdbc.execute(sqlDeleteStatistics);
        jdbc.execute(sqlCreateStatistics);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void statisticsDoNotUpdatePerRequest() {
        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();
        statisticsAspect.countPubliclyAvailableRequests();
        statisticsAspect.countBookListRequests();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(0, dbStatistics.getAdminOnlyRequests(), "Should be 0");
        assertSame(0, dbStatistics.getPubliclyAvailableRequests(), "Should be 0");
        assertSame(0, dbStatistics.getMenuEntries(), "Should be 0");
        assertSame(0, dbStatistics.getBookListEntries(), "Should be 0");
        assertSame(0, dbStatistics.getAuthorListEntries(), "Should be 0");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void statisticsUpdateWhenMenuRequested() {

        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();
        statisticsAspect.countPubliclyAvailableRequests();
        statisticsAspect.countBookListRequests();
        statisticsAspect.updateStatistics();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(1, dbStatistics.getAdminOnlyRequests(), "Should be 1");
        assertSame(1, dbStatistics.getPubliclyAvailableRequests(), "Should be 1");
        assertSame(1, dbStatistics.getMenuEntries(), "Should be 1");
        assertSame(1, dbStatistics.getBookListEntries(), "Should be 0");
        assertSame(1, dbStatistics.getAuthorListEntries(), "Should be 1");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getAdminHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/addBookForm")).andExpect(status().is3xxRedirection()).andReturn();
        statisticsAspect.countAdminRequests();

        assertTrue(statisticsService.getStatistics().getAdminOnlyRequests() == 0);

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getAdminOnlyRequests() == 1);
    }

    @Test
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getMenuHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getMenuEntries() == 1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPublicHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/list"));
        assertTrue(statisticsService.getStatistics().getPubliclyAvailableRequests() == 0);

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getPubliclyAvailableRequests() == 1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getBookListHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/list"));
        assertTrue(statisticsService.getStatistics().getBookListEntries() == 0);

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getBookListEntries() == 1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getAuthorListHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/authorsList"));
        assertTrue(statisticsService.getStatistics().getAuthorListEntries() == 0);

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getAuthorListEntries() == 1);
    }
}
