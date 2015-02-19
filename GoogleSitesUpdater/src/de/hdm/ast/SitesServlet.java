package de.hdm.ast;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.util.ServiceException;

import de.hdm.ast.drive.DriveUtil;
import de.hdm.ast.sites.SitesUtil;

/**
 * Servlet, dass über einen Cronjob angestoßen wird.
 * 
 * @author Fabian
 *
 */
@SuppressWarnings("serial")
public class SitesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			SitesUtil.getSiteFeed();
			SitesUtil.updateSite(DriveUtil.getMetadata());

		} catch (IOException | GeneralSecurityException | ServiceException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
