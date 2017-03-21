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
public class SuspendServlet extends HttpServlet {

    CrawlersService crawlersService = new CrawlersServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {

        // Getting params
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        long id = Long.parseLong(pathParts[2]);

        // Suspending and getting result of this action
        String result = crawlersService.suspend(id);

        // Output result of suspending
        response.getWriter().print(result);
    }
}
