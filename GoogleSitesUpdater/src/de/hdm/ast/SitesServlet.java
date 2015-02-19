package de.hdm.ast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.Category;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.SiteFeed;
import com.google.gdata.data.sites.TagCategory;
import com.google.gdata.data.sites.Theme;
import com.google.gdata.util.ServiceException;

import de.hdm.ast.drive.DriveUtil;
import de.hdm.ast.sites.Authorization;
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
