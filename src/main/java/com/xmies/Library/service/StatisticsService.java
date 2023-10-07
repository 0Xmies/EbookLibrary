package com.xmies.Library.service;

import com.xmies.Library.entity.Statistics;

public interface StatisticsService {

    Statistics getStatistics();

    void save(Statistics statistics);
}
