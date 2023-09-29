package com.xmies.Library.aspect;

import com.xmies.Library.entity.Statistics;
import com.xmies.Library.service.StatisticsService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticsAspect {

    private StatisticsService statisticsService;

    private Statistics statistics;

    @Autowired
    public StatisticsAspect(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        this.statistics = statisticsService.getStatistics();
    }

    @Before("com.xmies.Library.aspect.Expressions.forMenu()")
    public void updateStatistics() {
        statistics.setMenuEntries(statistics.getMenuEntries() +1);
        statisticsService.save(statistics);
    }

    @After("com.xmies.Library.aspect.Expressions.forAdminLibraryController() &&" +
            " !(com.xmies.Library.aspect.Expressions.forAllInitBinders())")
    public void countAdminRequests() {
        statistics.setAdminOnlyRequests(statistics.getAdminOnlyRequests() + 1);
    }

    @After("com.xmies.Library.aspect.Expressions.forUserLibraryController()" +
            " && !(com.xmies.Library.aspect.Expressions.forAllInitBinders())")
    public void countPubliclyAvailableRequests() {
        statistics.setPubliclyAvailableRequests(statistics.getPubliclyAvailableRequests() + 1);
    }

    @After("com.xmies.Library.aspect.Expressions.forBookList()" +
            " && !(com.xmies.Library.aspect.Expressions.forAllInitBinders())")
    public void countBookListRequests() {
        statistics.setBookListEntries(statistics.getBookListEntries() + 1);
    }

    @After("com.xmies.Library.aspect.Expressions.forAuthorList()" +
            " && !(com.xmies.Library.aspect.Expressions.forAllInitBinders())")
    public void countAuthorListRequests() {
        statistics.setAuthorListEntries(statistics.getAuthorListEntries() + 1);
    }
}
