package com.xmies.Library.aspect;

import com.xmies.Library.entity.Statistics;
import com.xmies.Library.service.StatisticsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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

    private Statistics beforeStatistics;

    @BeforeEach
    public void beforeEach() {
        beforeStatistics = statisticsService.getStatistics();
    }

    @AfterEach
    public void afterEach() {
        statisticsAspect.updateStatistics();
    }


    @Test
    public void statisticsDoNotUpdatePerRequest() {
        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();
        statisticsAspect.countPubliclyAvailableRequests();
        statisticsAspect.countBookListRequests();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(beforeStatistics.getAdminOnlyRequests(), dbStatistics.getAdminOnlyRequests());
        assertSame(beforeStatistics.getPubliclyAvailableRequests(), dbStatistics.getPubliclyAvailableRequests());
        assertSame(beforeStatistics.getMenuEntries(), dbStatistics.getMenuEntries());
        assertSame(beforeStatistics.getBookListEntries(), dbStatistics.getBookListEntries());
        assertSame(beforeStatistics.getAuthorListEntries(), dbStatistics.getAuthorListEntries());
    }

    @Test
    public void statisticsUpdateWhenRequested() {
        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();
        statisticsAspect.countPubliclyAvailableRequests();
        statisticsAspect.countBookListRequests();
        statisticsAspect.updateStatistics();

        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(beforeStatistics.getAdminOnlyRequests() + 1, dbStatistics.getAdminOnlyRequests());
        assertSame(beforeStatistics.getPubliclyAvailableRequests() + 1, dbStatistics.getPubliclyAvailableRequests());
        assertSame(beforeStatistics.getMenuEntries() + 1, dbStatistics.getMenuEntries());
        assertSame(beforeStatistics.getBookListEntries() + 1, dbStatistics.getBookListEntries());
        assertSame(beforeStatistics.getAuthorListEntries() + 1, dbStatistics.getAuthorListEntries());
    }

    @Test
    public void getAdminHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/addBookForm")
                .with(user("fakeAdmin").roles("USER", "ADMIN")));

        assertTrue(statisticsService.getStatistics().getAdminOnlyRequests() == beforeStatistics.getAdminOnlyRequests());

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getAdminOnlyRequests() == beforeStatistics.getAdminOnlyRequests() + 1);
    }

    @Test
    public void getMenuHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getMenuEntries() == beforeStatistics.getMenuEntries() + 1);
    }

    @Test
    public void getPublicHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/list"));
        assertTrue(statisticsService.getStatistics().getPubliclyAvailableRequests()
                == beforeStatistics.getPubliclyAvailableRequests());

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getPubliclyAvailableRequests()
                == beforeStatistics.getPubliclyAvailableRequests() + 1);
    }

    @Test
    public void getBookListHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/list"));
        assertTrue(statisticsService.getStatistics().getBookListEntries() == beforeStatistics.getBookListEntries());

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getBookListEntries() == beforeStatistics.getBookListEntries() + 1);
    }

    @Test
    public void getAuthorListHttpRequestIncreaseStats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/authorsList"));
        assertTrue(statisticsService.getStatistics().getAuthorListEntries() == beforeStatistics.getAuthorListEntries());

        mockMvc.perform(MockMvcRequestBuilders.get("/library/menu"));
        assertTrue(statisticsService.getStatistics().getAuthorListEntries() == beforeStatistics.getAuthorListEntries() + 1);
    }
}
