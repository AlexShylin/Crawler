package com.gd.ashylin.crawler.crawler.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Alexander Shylin
 */
public class JsoupDataSource implements PageDataSource {

    // 10 sec timeout
    private static final int TIMEOUT = 10000;

    static final String STATUS_CONNECTION_TIMEOUT = "Connection timeout";
    static final String STATUS_NOT_HTTP_OR_HTTPS = "Not HTTP or HTTPS binding";
    static final String STATUS_WRONG_PROTOCOL = "Wrong protocol";
    static final String STATUS_TOO_LONG_URL = "Too long url";

    public String getPageStatusCode(String url) {
        if (url.length() > 1536) {
            return STATUS_TOO_LONG_URL;
        }
        try {
            return String.valueOf(getConnection(url).execute().statusCode());
        } catch (IllegalArgumentException e) {
            return STATUS_WRONG_PROTOCOL;
        } catch (MalformedURLException e) {
            return STATUS_NOT_HTTP_OR_HTTPS;
        } catch (IOException e) {
            return STATUS_CONNECTION_TIMEOUT;
        }
    }

    private Connection getConnection(String url) {
        return Jsoup.connect(url).timeout(TIMEOUT).ignoreHttpErrors(true);
    }

    public Document getDocument(String url) throws IOException {
        return getConnection(url).get();
    }
}
