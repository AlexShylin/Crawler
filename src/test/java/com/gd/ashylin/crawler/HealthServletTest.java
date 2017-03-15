package com.gd.ashylin.crawler;

import com.gd.ashylin.crawler.health.HealthService;
import com.gd.ashylin.crawler.health.HealthServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class DemoServletTest {
    public static final String expectedResponse = "Hello, World!";

    @Test
    public void healthTest() {
        HealthService health = new HealthServiceImpl();
        String actualResponse = health.healthCheck();
        System.out.println(actualResponse);
        Assert.assertEquals(true, actualResponse.contains("OK"));
    }
}
