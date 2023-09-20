package com.xmies.Library.service;

import com.xmies.Library.entity.Statistics;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {

    Statistics getSiteStatistics();

    void save(Statistics statistics);

}
