package com.gd.ashylin.crawler;

import com.gd.ashylin.crawler.service.ResultsService;
import com.gd.ashylin.crawler.service.ResultsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Shylin
 */
public class ResultsServlet extends HttpServlet {

    ResultsService resultsService = new ResultsServiceImpl();

    public static long UNDEFINED_QUERY_BOUNDS = -1;
    public static String QUERY_BOUNDS_SPLITTER = "-";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {

        String result = null;

        // Getting request params, fetching results
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        long id = Long.parseLong(pathParts[2]);
        String thirdPathPart = pathParts[3];

        if (thirdPathPart == null || thirdPathPart.isEmpty()) {
            result = resultsService.getResult(id, UNDEFINED_QUERY_BOUNDS, UNDEFINED_QUERY_BOUNDS, "asc");
        } else {
            thirdPathPart = thirdPathPart.toLowerCase();
            switch (thirdPathPart) {
                case "concrete":
                case "con":
                    String concreteScan = pathParts[4];
                    if (concreteScan == null || concreteScan.isEmpty()) {
                        result = null;
                    } else {
                        result = resultsService.getScrapResult(id, Long.parseLong(pathParts[4]));
                    }
                    break;
                case "asc":
                case "desc":
                    String fromTo = pathParts[4];
                    if (fromTo == null || fromTo.isEmpty()) {
                        result = resultsService.getResult(id, UNDEFINED_QUERY_BOUNDS, UNDEFINED_QUERY_BOUNDS, thirdPathPart);
                    } else {
                        String[] fromToSplitted = fromTo.split(QUERY_BOUNDS_SPLITTER);
                        long from = Long.parseLong(fromToSplitted[0]);
                        long to = Long.parseLong(fromToSplitted[0]);
                        result = resultsService.getResult(id, from, to, thirdPathPart);
                    }
            }
        }

        // Sending result
        response.getWriter().print(result);
    }
}
