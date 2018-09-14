package com.gd.ashylin.crawler.dto;

import com.gd.ashylin.crawler.db.entity.ScrapResult;

import java.util.List;

/**
 * @author Alexander Shylin
 */
public class ScrapDto {
    List<ScrapResult> scrapResults;

    public ScrapDto() {
    }

    public ScrapDto(List<ScrapResult> scrapResults) {
        this.scrapResults = scrapResults;
    }

    public List<ScrapResult> getScrapResults() {
        return scrapResults;
    }
}
