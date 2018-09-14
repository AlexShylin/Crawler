package com.gd.ashylin.crawler.controller.v1;

import com.gd.ashylin.crawler.bindings.ScrapResultBinding;
import com.gd.ashylin.crawler.bindings.request.v1.LaunchRequestBindingV1;
import com.gd.ashylin.crawler.bindings.response.*;
import com.gd.ashylin.crawler.db.entity.ScrapResult;
import com.gd.ashylin.crawler.dto.*;
import com.gd.ashylin.crawler.service.v1.CrawlersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shylin
 */
@RestController
@RequestMapping("/v1/crawlers")
public class CrawlersRestControllerV1 {

    private CrawlersService crawlersService;


    /*
     * POST method
     */

    @RequestMapping(method = RequestMethod.POST)
    public LaunchResponseBinding launchCrawlerRequest(@RequestBody LaunchRequestBindingV1 requestBinding) {
        LaunchDto launchDto = crawlersService.launch(requestBinding.getUrl(), requestBinding.getDelay());
        return new LaunchResponseBinding(launchDto.getId());
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    public CancelResponseBinding cancelCrawlerPerformance(@PathVariable Long id) {
        CancelDto cancelDto = crawlersService.cancel(id);
        return new CancelResponseBinding(cancelDto.getStatus());
    }


    /*
     * GET method
     */

    @RequestMapping(value = "/{id}/results", params = {"sort", "offset", "page_size"}, method = RequestMethod.GET)
    public ResultsResponseBinding getCrawlersResults(@PathVariable Long id,
                                                     @RequestParam(value = "sort") String sort,
                                                     @RequestParam(value = "offset") Integer offsetVal,
                                                     @RequestParam(value = "page_size") Integer pageSize) {
        ResultsDto resultsDto = crawlersService.getResult(id, sort, offsetVal, pageSize);
        return new ResultsResponseBinding(resultsDto.getId(),
                resultsDto.getStatus(),
                resultsDto.getTimestampLaunch(),
                resultsDto.getTimestampFinish(),
                resultsDto.getUrl(),
                resultsDto.getThreads(),
                resultsDto.getDelay(),
                transferScrapResultsToBinding(resultsDto.getResults()));
    }

    @RequestMapping(value = "/{id}/status", method = RequestMethod.GET)
    public StatusResponseBinding getCrawlerStatus(@PathVariable Long id) {
        StatusDto statusDto = crawlersService.getStatus(id);
        return new StatusResponseBinding(statusDto.getStatus());
    }

    @RequestMapping(value = "/{id}/scraps", params = {"search", "field"}, method = RequestMethod.GET)
    public ScrapResponseBinding getCrawlersScraps(@PathVariable Long id,
                                                  @RequestParam(value = "search") String searchValue,
                                                  @RequestParam(value = "field") String fieldValue) {
        ScrapDto scrapDto = crawlersService.getScrapResults(id, searchValue, fieldValue);
        return new ScrapResponseBinding(transferScrapResultsToBinding(scrapDto.getScrapResults()));
    }

    private List<ScrapResultBinding> transferScrapResultsToBinding(List<ScrapResult> scrapResults) {
        List<ScrapResultBinding> scrapResultBindings = new ArrayList<>();

        for (ScrapResult res : scrapResults) {
            scrapResultBindings.add(new ScrapResultBinding(res.getId(), res.getUrl(), res.getSourceurl(), res.getStatus(), res.getResponseTime()));
        }

        return scrapResultBindings;
    }

    @Autowired
    public void setCrawlersService(CrawlersService crawlersService) {
        this.crawlersService = crawlersService;
    }
}
