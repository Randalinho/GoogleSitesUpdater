package de.hdm.ast.sites;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.Category;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.sites.SiteEntry;
import com.google.gdata.data.sites.SiteFeed;
import com.google.gdata.data.sites.TagCategory;
import com.google.gdata.data.sites.Theme;
import com.google.gdata.util.ServiceException;

public class SitesUtil {

	
	public static String getSiteFeedUrl() {

		String domain = "prestige-worldwide.in"; // OR if the Site is hosted on
													// Google Apps, your domain
													// (e.g. example.com)
		return "https://sites.google.com/feeds/site/" + domain + "/";
	}

	public static void getSiteFeed() throws IOException, ServiceException,
			GeneralSecurityException {

		SitesService client = Authorization.getSitesService();

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

	public static SiteEntry createSite(String title, String summary, String theme,
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
