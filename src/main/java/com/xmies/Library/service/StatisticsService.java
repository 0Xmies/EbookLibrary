package com.xmies.Library.service;

import com.xmies.Library.entity.Statistics;
import org.springframework.stereotype.Service;

public interface StatisticsService {

    Statistics getStatistics();

    void save(Statistics statistics);
}
