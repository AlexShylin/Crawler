package com.gd.ashylin.crawler.service.v2;

import com.gd.ashylin.crawler.dto.*;

/**
 * @author Alexander Shylin
 */
public interface CrawlersService {
    LaunchDto launch(String url, Integer threads, Long delay, Boolean enableCaching);

    CancelDto cancel(Long id);

    ResultsDto getResult(Long id, String sort, Integer offset, Integer pageSize);

    StatusDto getStatus(Long id);

    ScrapDto getScrapResults(Long id, String search, String field);
}
