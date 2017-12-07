package com.gd.ashylin.crawler.crawler.util;

import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public interface PageDataSource {
    String getPageStatusCode(String url);

    Object getDocument(String url) throws IOException;
}
