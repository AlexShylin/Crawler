package com.gd.ashylin.crawler.service.v1;

import com.gd.ashylin.crawler.dto.*;

/**
 * @author Alexander Shylin
 */
public interface CrawlersService {
    LaunchDto launch(String url, Long delay);

    CancelDto cancel(Long id);

    ResultsDto getResult(Long id, String sort, Integer offset, Integer pageSize);

    StatusDto getStatus(Long id);

    ScrapDto getScrapResults(Long id, String search, String field);
}
