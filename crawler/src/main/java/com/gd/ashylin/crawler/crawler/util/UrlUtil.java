package com.gd.ashylin.crawler.crawler.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Alexander Shylin
 */
public class UrlUtil {

    public String convertPathToAbsolute(String domain, String currentUrl, boolean validate) throws MalformedURLException {
        if (!isAbsolutePath(currentUrl)) {
            if (currentUrl.startsWith("/")) {
                currentUrl = removeEndingSlashes(domain) + currentUrl;
            } else {
                currentUrl = domain + currentUrl;
            }
        }
        if (validate) {
            validateUrl(currentUrl);
        }
        return removeEndingSlashes(currentUrl);
    }

    private void validateUrl(String url) throws MalformedURLException {
        new URL(url);
    }

    private boolean isAbsolutePath(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public boolean isInternalUrl(String domain, String url) {
        // Getting domain name (e.g. "site.com" from "http://site.com/aaa/sss?param=value&param2=value2")
        String urlDomainRoot = domain.split("/")[2];
        String urlDomainComparable = url.split("/")[2];

        /*
         * both contains are for cases when some url looks like
         * "support.site.com" but another is "site.com"
         */
        return urlDomainRoot.equals(urlDomainComparable) ||
                urlDomainRoot.contains(urlDomainComparable) ||
                urlDomainComparable.contains(urlDomainRoot);

    }

    public String removeEndingSlashes(String url) {
        int i = url.length() - 1;
        while (url.charAt(i) == '/') {
            i--;
        }
        return url.substring(0, i + 1);
    }
}
