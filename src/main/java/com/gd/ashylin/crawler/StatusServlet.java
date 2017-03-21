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
public class StatusServlet extends HttpServlet {

    ResultsService resultsService = new ResultsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {

        // Getting request params
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        long id = Long.parseLong(pathParts[2]);

        // Getting status
        String status = resultsService.getStatus(id);

        // Sending result
        response.getWriter().print(status);
    }

}
