package com.gd.ashylin.crawler;

import org.junit.Assert;
import org.junit.Test;

public class DemoServletTest {
//    public static final String healthUrl = "http://localhost:8080/servlet/health";
    public static final String expectedResponse = "Hello, World!";

//    @Test
//    public void healthDoGetPositiveTest() {
//        try {
//            URL url = new URL(healthUrl);
//            URLConnection connection = url.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String result = null;
//
//            while ((result = in.readLine()) != null) {
//                Assert.assertEquals(expectedResponse, result);
//            }
//            in.close();
//        } catch (IOException ex) {
//            // TODO logging
//        }
//    }

    @Test
    public void healthTest() {
        HealthService health = new HealthServiceImpl();
        String actualResponse = health.healthCheck();
        Assert.assertEquals(expectedResponse, actualResponse);
    }
}
