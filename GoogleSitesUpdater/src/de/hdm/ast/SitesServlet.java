package de.hdm.ast;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet, dass �ber einen Cronjob angesto�en wird.
 * 
 * @author Fabian
 *
 */
@SuppressWarnings("serial")
public class SitesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.print("Job wurde erfolgreich asugef�hrt");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
