package com.test;

import java.io.*;
import javax.servlet.http.*;

public class DemoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().println("Howdy, partner");
	}
}
