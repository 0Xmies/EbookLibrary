package com.xmies.Library.service;

import com.xmies.Library.entity.Statistics;
import com.xmies.Library.repository.StatisticsRepository;
import jakarta.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Statistics getStatistics() {
        return this.getStatistics(1);
    }

    public Statistics getStatistics(int id) {
        int tempId = id;
        Optional<Statistics> result = statisticsRepository.findById(id);
        Statistics statistics;

        if (result.isPresent()) {
            statistics = result.get();
        } else {
            statistics = new Statistics
                    (0,0,0,0,0);
            statisticsRepository.save(statistics);
            System.out.println("Created new statistics");
        }

        return statistics;
    }

    @Override
    public void save(Statistics statistics) {
        statisticsRepository.save(statistics);
    }
}
