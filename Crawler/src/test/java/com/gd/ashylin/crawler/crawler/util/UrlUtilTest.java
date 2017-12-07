package com.gd.ashylin.crawler.crawler.util;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * @author Alexander Shylin
 */
public class UrlUtilTest {

    private UrlUtil urlUtil = new UrlUtil();

    @Test
    public void testConvertPathToAbsolute() throws MalformedURLException {
        String domain = "https://google.com/";
        String relativePath = "/search/";
        String absolutePath = "https://google.com/search";

        String obtainedPath = urlUtil.convertPathToAbsolute(domain, relativePath, true);

        Assert.assertEquals(absolutePath, obtainedPath);
    }

    @Test(expected = MalformedURLException.class)
    public void testConvertPathToAbsoluteInvalidDomain() throws MalformedURLException {
        String url = "wrong";
        String invalidDomain = "htt://google.com/";

        urlUtil.convertPathToAbsolute(invalidDomain, url, true);
    }

    @Test
    public void testRemoveEndingSlashes() {
        String strWithEndingSlashes = "string///";
        String strWithoutEndingSlashes = "string";

        String obtainedString = urlUtil.removeEndingSlashes(strWithEndingSlashes);

        Assert.assertEquals(strWithoutEndingSlashes, obtainedString);
    }

    @Test
    public void testIsInternalUrl() {
        String domain = "https://google.com/";
        String url = "https://translate.google.com";

        Assert.assertTrue(urlUtil.isInternalUrl(domain, url));
    }


}
