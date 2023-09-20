package com.xmies.Library.service;

import com.xmies.Library.entity.Statistics;
import com.xmies.Library.repository.StatisticsRepository;
import org.springframework.context.annotation.Bean;

public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsRepository statisticsRepository;

    @Override
    @Bean
    public Statistics getSiteStatistics() {

        int siteStatistics = 1;
        return statisticsRepository.getById(siteStatistics);
    }

    @Override
    public void save(Statistics statistics) {
        statisticsRepository.save(statistics);
    }
}
