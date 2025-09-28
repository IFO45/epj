package com.example;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet("/reqdetails")
public class RequestDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<h2>Request Details</h2>");
        out.println("Method: " + req.getMethod() + "<br>");
        out.println("URI: " + req.getRequestURI() + "<br>");
        out.println("Remote Addr: " + req.getRemoteAddr() + "<br>");
        out.println("<h3>Headers</h3><ul>");
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            out.println("<li>" + name + " : " + req.getHeader(name) + "</li>");
        }
        out.println("</ul>");
    }
}
