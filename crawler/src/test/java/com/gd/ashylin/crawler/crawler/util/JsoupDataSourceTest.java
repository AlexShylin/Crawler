package com.gd.ashylin.crawler.crawler.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Shylin
 */
public class JsoupDataSourceTest {

    private JsoupDataSource pageDataSource = new JsoupDataSource();

    @Test
    public void testGetPageStatusCode() {
        String url = "https://google.com";
        String expectedCode = "200";

        String obtainedCode = pageDataSource.getPageStatusCode(url);

        Assert.assertEquals(expectedCode, obtainedCode);
    }

    @Test
    public void testGetPageStatusCodeWrongProtocol() {
        String url = "google.com";
        String expectedStatus = JsoupDataSource.STATUS_WRONG_PROTOCOL;

        String obtainedStatus = pageDataSource.getPageStatusCode(url);

        Assert.assertEquals(expectedStatus, obtainedStatus);
    }

    @Test
    public void testGetPageStatusCodeNotHttpOrHttps() {
        String url = "ftp://google.com";
        String expectedStatus = JsoupDataSource.STATUS_NOT_HTTP_OR_HTTPS;

        String obtainedStatus = pageDataSource.getPageStatusCode(url);

        Assert.assertEquals(expectedStatus, obtainedStatus);
    }

    @Test
    public  void testGetPageStatusCodeTooLong() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1600; i++) {
            stringBuilder.append("1");
        }

        String expectedStatus = JsoupDataSource.STATUS_TOO_LONG_URL;

        String obtainedStatus = pageDataSource.getPageStatusCode(stringBuilder.toString());

        Assert.assertEquals(expectedStatus, obtainedStatus);
    }

    @Test
    public void testGetPageStatusCodeConnectionTimeOut() {
        /*
         * non-reachable address:
         *  - "http://10.255.255.1"
         *  - "http://10.0.0.0"
         *  - "http://10.255.255.255"
         *  - "http://172.16.0.0"
         *  - "http://172.31.255.255"
         *  - "http://192.168.0.0"
         *  - "http://192.168.255.255"
         *  - "https://google.com:81", but in some cases it might be reachable (not recommended)
         */
        String url = "http://10.255.255.1/";
        String expectedStatus = JsoupDataSource.STATUS_CONNECTION_TIMEOUT;

        String obtainedStatus = pageDataSource.getPageStatusCode(url);

        Assert.assertEquals(expectedStatus, obtainedStatus);
    }
}
