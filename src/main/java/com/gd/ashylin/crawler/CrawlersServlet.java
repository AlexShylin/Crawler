package com.gd.ashylin.crawler;

import com.gd.ashylin.crawler.service.CrawlersService;
import com.gd.ashylin.crawler.service.CrawlersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public class CrawlersServlet extends HttpServlet {

    CrawlersService crawlersService = new CrawlersServiceImpl();

    public static int DEFAULT_THREADS_NUMBER = 1;
    public static long DEFAULT_DELAY = 50L;

    public static final String verV1 = "v1";
    public static final String verV2 = "v2";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {

        // Getting params
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String url = pathParts[2];
        String version = pathParts[3];

        long delay = DEFAULT_DELAY;
        String delayParam = request.getParameter("delay");
        if (delayParam != null && !delayParam.isEmpty()) {
            delay = Integer.parseInt(delayParam);
        }

        int threadsNum  = DEFAULT_THREADS_NUMBER;

        switch (version) {
            case verV1:
                break;
            case verV2:
                String threadsNumParam = request.getParameter("thrd");
                if (threadsNumParam != null && !threadsNumParam.isEmpty()) {
                    threadsNum = Integer.parseInt(threadsNumParam);
                }
                break;
        }

        if (threadsNum < 0) {
            // output error
        }

        // Suspending and getting result of this action
        String result = crawlersService.crawler(url, threadsNum, delay);

        // Output result of suspending
        response.getWriter().print(result);
    }
}
