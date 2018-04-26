package com.arjvik.arjmart.web.debug;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Servlet implementation class DebugSessionServlet
 */
public class DebugSessionServlet extends HttpServlet implements HttpSessionListener {
	private static final long serialVersionUID = 1L;

	private static final LinkedHashSet<HttpSession> sessions = new LinkedHashSet<>();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!"localhost".equals(request.getServerName()) || request.getServerPort() != 8080) {
			response.setStatus(403);
			PrintWriter out = response.getWriter();
			out.println("<html><body><h1>Sorry, this servlet is only accessable during development</h1></body></html>");
			out.close();
			return;
		}
		
		if(!"Basic YXJqdmlrOmFiY2Q=".equals(request.getHeader("Authorization"))) {
			response.setStatus(401);
			response.setHeader("WWW-Authenticate", "Basic realm=realm");
			return;
		}
		PrintWriter out = response.getWriter();
		out.print("<html><body><h1>List of Sessions:</h1>");
		int i = 1;
		for (HttpSession session : sessions) {
			out.printf("<h1>Session #%d</h1>",i++);
			out.print("<table><tr><th>Key</th><th>Value</th></tr>");
			Enumeration<String> e = session.getAttributeNames();
			while(e.hasMoreElements()){
				String key = e.nextElement();
				out.printf("<tr><td>%s</td><td>%s</td></tr>",key,session.getAttribute(key).toString());
			}
			out.print("</table><hr/>");
		}
		out.print("</body></html>");
		out.close();
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		sessions.add(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		sessions.remove(se.getSession());
	}

}
