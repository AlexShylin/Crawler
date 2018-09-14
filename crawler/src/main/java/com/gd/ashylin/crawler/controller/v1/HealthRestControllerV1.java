package com.gd.ashylin.crawler.controller.v1;

import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import com.gd.ashylin.crawler.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alexander Shylin
 */

@RestController
@RequestMapping("/v1/health")
public class HealthRestControllerV1 {

    private HealthService healthService;

    @RequestMapping(method = RequestMethod.GET)
    public DbMetadata health() {
        return healthService.healthCheck();
    }

    @Autowired
    public void setHealthService(HealthService healthService) {
        this.healthService = healthService;
    }
}
