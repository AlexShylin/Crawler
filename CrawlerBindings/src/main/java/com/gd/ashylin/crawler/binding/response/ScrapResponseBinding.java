package com.gd.ashylin.crawler.binding.response;

import com.gd.ashylin.crawler.binding.ScrapResultBinding;

import java.util.List;

/**
 * @author Alexander Shylin
 */
public class ScrapResponseBinding {
    private List<ScrapResultBinding> scrapResults;

    public ScrapResponseBinding() {
    }

    public ScrapResponseBinding(List<ScrapResultBinding> scrapResults) {
        this.scrapResults = scrapResults;
    }

    public List<ScrapResultBinding> getScrapResults() {
        return scrapResults;
    }
}
