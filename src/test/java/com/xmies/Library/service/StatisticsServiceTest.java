package com.xmies.Library.service;

import com.xmies.Library.aspect.StatisticsAspect;
import com.xmies.Library.entity.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertSame;

@TestPropertySource("/application-test.properties")
@SpringBootTest
public class StatisticsServiceTest {

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
    public void getStatisticsTest() {
        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(0, dbStatistics.getAdminOnlyRequests(), "Should be 0");
        assertSame(0, dbStatistics.getPubliclyAvailableRequests(), "Should be 0");
        assertSame(0, dbStatistics.getMenuEntries(), "Should be 0");
        assertSame(0, dbStatistics.getBookListEntries(), "Should be 0");
        assertSame(0, dbStatistics.getAuthorListEntries(), "Should be 0");
    }

    @Test
    public void statisticsDoNotUpdatePerRequestTest() {
        statisticsAspect.countAdminRequests();
        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(0, dbStatistics.getAdminOnlyRequests(), "Should be 0");
        assertSame(0, dbStatistics.getPubliclyAvailableRequests(), "Should be 0");
        assertSame(0, dbStatistics.getMenuEntries(), "Should be 0");
        assertSame(0, dbStatistics.getBookListEntries(), "Should be 0");
        assertSame(0, dbStatistics.getAuthorListEntries(), "Should be 0");
    }

    @Test
    public void statisticsUpdateWhenMenuRequestedTest() {
        statisticsAspect.countAdminRequests();
        statisticsAspect.countAuthorListRequests();
        statisticsAspect.countAdminRequests();
        statisticsAspect.countPubliclyAvailableRequests();
        statisticsAspect.updateStatistics();

        Statistics dbStatistics = statisticsService.getStatistics();

        assertSame(2, dbStatistics.getAdminOnlyRequests(), "Should be 2");
        assertSame(1, dbStatistics.getPubliclyAvailableRequests(), "Should be 1");
        assertSame(1, dbStatistics.getMenuEntries(), "Should be 1");
        assertSame(0, dbStatistics.getBookListEntries(), "Should be 0");
        assertSame(1, dbStatistics.getAuthorListEntries(), "Should be 1");
    }


}
