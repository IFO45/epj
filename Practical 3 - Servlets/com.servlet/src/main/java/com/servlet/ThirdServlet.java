package com.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/third")
public class ThirdServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		HttpSession session = req.getSession(false); // Using getSession(false) is safer if the session might not exist
		
		String name = "Kartik";
		String email = "kartik@gmail.com";
		
		// Only proceed if a session exists
		if (session != null) {
			
			// Read name from HttpSession
			Object nameObj = session.getAttribute("name");
			if (nameObj != null) {
				// The original code was: String name = (String) session.getAttribute("name");
				name = (String) nameObj;
			}
			
			// Read email from HttpSession
			Object emailObj = session.getAttribute("email");
			if (emailObj != null) {
				// The original code caused the error: String email = session.getAttribute("email").toString();
				// This fix checks for null before calling toString()
				email = emailObj.toString();
			}
		}
		
		// print readed details
		resp.getWriter().println("<html><body>");
		resp.getWriter().println("<h1>Session Details</h1>");
		resp.getWriter().println("Name : " + name + "<br>");
		resp.getWriter().println("Email : " + email);
		resp.getWriter().println("</body></html>");
	}
}
