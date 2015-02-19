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

import de.hdm.ast.sites.Authorization;

/**
 * Servlet, dass über einen Cronjob angestoßen wird.
 * 
 * @author Fabian
 *
 */
@SuppressWarnings("serial")
public class SitesServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// try {
		// DriveUtil.printAllFiles();
		// } catch (IOException e) {
		// // TODO: handle exception
		// }
		try {
			SiteEntry newSiteEntry = createSite("title", "summary for site", "slate", "tag");
			System.out.println("Name der neuen Seite: " + newSiteEntry.getSiteName());
			getSiteFeed();
			System.out.println("Feed erfolgreich geladen");
		} catch (IOException | GeneralSecurityException | ServiceException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public String getSiteFeedUrl() {

		String domain = "goileseite"; // OR if the Site is hosted on Google
										// Apps, your domain (e.g. example.com)
		return "https://sites.google.com/feeds/site" + domain + "/";
	}

	public void getSiteFeed() throws IOException, ServiceException,
			GeneralSecurityException {

		System.out.println("Autorisierung wird angestoßen");
		SitesService client = Authorization.getSitesService();
		System.out.println("Autorisierung erfolgreich");

		SiteFeed siteFeed = client.getFeed(new URL(getSiteFeedUrl()),
				SiteFeed.class);
		System.out.println(siteFeed.getTitle());

		for (SiteEntry entry : siteFeed.getEntries()) {
			System.out.println("Feed erfolgreich geladen");
			System.out.println("title: " + entry.getTitle().getPlainText());
			System.out.println("site name: " + entry.getSiteName().getValue());
			System.out.println("theme: " + entry.getTheme().getValue());
			System.out.println("");
		}
	}

	public SiteEntry createSite(String title, String summary, String theme,
			String tag) throws MalformedURLException, IOException,
			ServiceException, GeneralSecurityException {
		
		SitesService client = Authorization.getSitesService();
		
		SiteEntry entry = new SiteEntry();
		entry.setTitle(new PlainTextConstruct(title));
		entry.setSummary(new PlainTextConstruct(summary));

		Theme tt = new Theme();
		tt.setValue(theme);
		entry.setTheme(tt);

		entry.getCategories().add(
				new Category(TagCategory.Scheme.TAG, tag, null));

		return client.insert(new URL(getSiteFeedUrl()), entry);
	}

}
